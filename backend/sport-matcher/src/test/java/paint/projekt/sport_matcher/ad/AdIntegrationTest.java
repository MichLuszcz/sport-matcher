package paint.projekt.sport_matcher.ad;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import paint.projekt.sport_matcher.SportMatcherApplication;
import paint.projekt.sport_matcher.user.User;
import paint.projekt.sport_matcher.user.UserRepository;
import paint.projekt.sport_matcher.sportType.SportType;
import paint.projekt.sport_matcher.sportType.SportTypeRepository;
import paint.projekt.sport_matcher.utils.DummyData;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SportMatcherApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private JavaMailSender fakeEmailSender;

    @Autowired
    DummyData data;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SportTypeRepository sportTypeRepository;

    @Autowired
    private AdRepository adRepository;

    @BeforeEach
    public void setupDatabase() {
        data.deleteAll();
        data.addDummyData();
        adRepository.deleteAll(); // Clear ads to ensure a clean state for each test
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/ads";
    }

    @Test
    void createAdSuccessfully() {
        AdDTO adDTO = AdDTO.builder()
                .userId(data.user_john.getId())
                .sportTypeId(data.sportType_football.getId())
                .title("Looking for a football partner")
                .description("Friendly match at the local park.")
                .dateStart(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now().plusDays(1))
                .timeStart(LocalTime.of(18, 0))
                .timeEnd(LocalTime.of(20, 0))
                .location("Local Park")
                .participants(4)
                .build();

        ResponseEntity<AdDTO> response = restTemplate.postForEntity(getBaseUrl(), adDTO, AdDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Looking for a football partner", response.getBody().getTitle());
        assertEquals(data.user_john.getId(), response.getBody().getUserId());
        assertEquals(data.sportType_football.getId(), response.getBody().getSportTypeId());
        assertTrue(response.getBody().getIsActive());
        assertNotNull(response.getBody().getCreationDatetime());
    }

    @Test
    void getAllAds() {
        // Create a few ads first
        AdDTO ad1 = AdDTO.builder()
                .userId(data.user_mike.getId())
                .sportTypeId(data.sportType_basketball.getId())
                .title("Ad 1")
                .dateStart(LocalDate.now())
                .timeStart(LocalTime.now())
                .location("Loc 1")
                .participants(2)
                .build();
        restTemplate.postForEntity(getBaseUrl(), ad1, AdDTO.class);

        AdDTO ad2 = AdDTO.builder()
                .userId(data.user_jane.getId())
                .sportTypeId(data.sportType_tennis.getId())
                .title("Ad 2")
                .dateStart(LocalDate.now())
                .timeStart(LocalTime.now())
                .location("Loc 2")
                .participants(3)
                .build();
        restTemplate.postForEntity(getBaseUrl(), ad2, AdDTO.class);

        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl(), List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        // Further checks can be done by parsing the list and checking individual ad properties
        // For simplicity, we check if the titles are present using JsonPath on the raw response body
        String jsonResponse = restTemplate.getForObject(getBaseUrl(), String.class);
        JSONArray titles = JsonPath.read(jsonResponse, "$[*].title");
        assertTrue(titles.contains("Ad 1"));
        assertTrue(titles.contains("Ad 2"));
    }

    @Test
    void getAdByIdExists() {
        // Create an ad to retrieve
        AdDTO createdAdDTO = restTemplate.postForEntity(
                        getBaseUrl(),
                        AdDTO.builder()
                                .userId(testUser.getId())
                                .sportTypeId(testSportType.getId())
                                .title("Specific Ad")
                                .dateStart(LocalDate.now())
                                .timeStart(LocalTime.now())
                                .location("Specific Location")
                                .participants(1)
                                .build(),
                        AdDTO.class)
                .getBody();
        assertNotNull(createdAdDTO);
        Long adId = createdAdDTO.getId();

        ResponseEntity<AdDTO> response = restTemplate.getForEntity(getBaseUrl() + "/" + adId, AdDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(adId, response.getBody().getId());
        assertEquals("Specific Ad", response.getBody().getTitle());
        assertEquals("Specific Location", response.getBody().getLocation());
    }

    @Test
    void getAdByIdNotFound() {
        ResponseEntity<AdDTO> response = restTemplate.getForEntity(getBaseUrl() + "/99999", AdDTO.class); // Non-existent ID
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAdsByUserId() {
        // Create ads for testUser
        AdDTO adForUser1_1 = AdDTO.builder()
                .userId(testUser.getId())
                .sportTypeId(testSportType.getId())
                .title("User 1 Ad 1")
                .dateStart(LocalDate.now())
                .timeStart(LocalTime.now())
                .location("User 1 Loc 1")
                .participants(2)
                .build();
        restTemplate.postForEntity(getBaseUrl(), adForUser1_1, AdDTO.class);

        AdDTO adForUser1_2 = AdDTO.builder()
                .userId(testUser.getId())
                .sportTypeId(testSportType.getId())
                .title("User 1 Ad 2")
                .dateStart(LocalDate.now())
                .timeStart(LocalTime.now())
                .location("User 1 Loc 2")
                .participants(3)
                .build();
        restTemplate.postForEntity(getBaseUrl(), adForUser1_2, AdDTO.class);

        // Create another user and an ad for them to ensure separation
//        User otherUser = userRepository.save(User.builder().username("otheruser").email("other@example.com").password("password").build());
        AdDTO adForOtherUser = AdDTO.builder()
                .userId(otherUser.getId())
                .sportTypeId(testSportType.getId())
                .title("Other User Ad")
                .dateStart(LocalDate.now())
                .timeStart(LocalTime.now())
                .location("Other Loc")
                .participants(1)
                .build();
        restTemplate.postForEntity(getBaseUrl(), adForOtherUser, AdDTO.class);


        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl() + "/user/" + testUser.getId(), List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        // Verify titles using JsonPath
        String jsonResponse = restTemplate.getForObject(getBaseUrl() + "/user/" + testUser.getId(), String.class);
        JSONArray titles = JsonPath.read(jsonResponse, "$[*].title");
        assertTrue(titles.contains("User 1 Ad 1"));
        assertTrue(titles.contains("User 1 Ad 2"));
    }
}