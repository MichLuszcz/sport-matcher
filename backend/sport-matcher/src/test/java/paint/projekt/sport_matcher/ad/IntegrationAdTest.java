package paint.projekt.sport_matcher.ad;

import com.jayway.jsonpath.JsonPath;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import paint.projekt.sport_matcher.SportMatcherApplication;
import paint.projekt.sport_matcher.user.UserRepository;
import paint.projekt.sport_matcher.sportType.SportTypeRepository;
import paint.projekt.sport_matcher.utils.DummyData;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static paint.projekt.sport_matcher.utils.UrlBuilder.buildUrl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SportMatcherApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IntegrationAdTest {

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
    HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setupDatabase() {
        data.deleteAll();
        data.addDummyData();
        adRepository.deleteAll(); // Clear ads to ensure a clean state for each test
        headers.clear();
    }

    @Test
    void createAdSuccessfully() {

        AdCreationRequest ad1 = new AdCreationRequest(1L, "ad1", "descr1", LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), "Warszawa", 2);

        var response = restTemplate.exchange(
                buildUrl("/api/ads/", port),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class
        );
        var json = JsonPath.parse(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Looking for a football partner", json.read("$.title"));
//        assertEquals(data.user_john.getId(), json.read("$.title");
        assertEquals(data.sportType_football.getId(), json.read("$.sportId"));
        assertTrue(json.read("$.isActive"));
        assertNotNull(json.read("$.creationDateTime"));
    }

//    @Test
//    void getAllAds() {
//        // football
//
//        restTemplate.postForEntity(getBaseUrl(), ad1, AdDTO.class);
//        var response = restTemplate.exchange(
//                buildUrl("/api/ads/1", port),
//                HttpMethod.GET,
//                new HttpEntity<>(headers),
//                String.class
//        );
//
//        AdDTO ad2 = AdDTO.builder()
//                .userId(data.user_jane.getId())
//                .sportTypeId(data.sportType_tennis.getId())
//                .title("Ad 2")
//                .dateStart(LocalDate.now())
//                .timeStart(LocalTime.now())
//                .location("Loc 2")
//                .participants(3)
//                .build();
//        restTemplate.postForEntity(getBaseUrl(), ad2, AdDTO.class);
//
//        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl(), List.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(2, response.getBody().size());
//
//        // Further checks can be done by parsing the list and checking individual ad properties
//        // For simplicity, we check if the titles are present using JsonPath on the raw response body
//        String jsonResponse = restTemplate.getForObject(getBaseUrl(), String.class);
//        JSONArray titles = JsonPath.read(jsonResponse, "$[*].title");
//        assertTrue(titles.contains("Ad 1"));
//        assertTrue(titles.contains("Ad 2"));
//    }
//
//    @Test
//    void getAdByIdExists() {
//        // Create an ad to retrieve
//        AdDTO createdAdDTO = restTemplate.postForEntity(
//                        getBaseUrl(),
//                        AdDTO.builder()
//                                .userId(testUser.getId())
//                                .sportTypeId(testSportType.getId())
//                                .title("Specific Ad")
//                                .dateStart(LocalDate.now())
//                                .timeStart(LocalTime.now())
//                                .location("Specific Location")
//                                .participants(1)
//                                .build(),
//                        AdDTO.class)
//                .getBody();
//        assertNotNull(createdAdDTO);
//        Long adId = createdAdDTO.getId();
//
//        ResponseEntity<AdDTO> response = restTemplate.getForEntity(getBaseUrl() + "/" + adId, AdDTO.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(adId, response.getBody().getId());
//        assertEquals("Specific Ad", response.getBody().getTitle());
//        assertEquals("Specific Location", response.getBody().getLocation());
//    }
//
//    @Test
//    void getAdByIdNotFound() {
//        ResponseEntity<AdDTO> response = restTemplate.getForEntity(getBaseUrl() + "/99999", AdDTO.class); // Non-existent ID
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    void getAdsByUserId() {
//        // Create ads for testUser
//        AdDTO adForUser1_1 = AdDTO.builder()
//                .userId(testUser.getId())
//                .sportTypeId(testSportType.getId())
//                .title("User 1 Ad 1")
//                .dateStart(LocalDate.now())
//                .timeStart(LocalTime.now())
//                .location("User 1 Loc 1")
//                .participants(2)
//                .build();
//        restTemplate.postForEntity(getBaseUrl(), adForUser1_1, AdDTO.class);
//
//        AdDTO adForUser1_2 = AdDTO.builder()
//                .userId(testUser.getId())
//                .sportTypeId(testSportType.getId())
//                .title("User 1 Ad 2")
//                .dateStart(LocalDate.now())
//                .timeStart(LocalTime.now())
//                .location("User 1 Loc 2")
//                .participants(3)
//                .build();
//        restTemplate.postForEntity(getBaseUrl(), adForUser1_2, AdDTO.class);
//
//        // Create another user and an ad for them to ensure separation
////        User otherUser = userRepository.save(User.builder().username("otheruser").email("other@example.com").password("password").build());
//        AdDTO adForOtherUser = AdDTO.builder()
//                .userId(otherUser.getId())
//                .sportTypeId(testSportType.getId())
//                .title("Other User Ad")
//                .dateStart(LocalDate.now())
//                .timeStart(LocalTime.now())
//                .location("Other Loc")
//                .participants(1)
//                .build();
//        restTemplate.postForEntity(getBaseUrl(), adForOtherUser, AdDTO.class);
//
//
//        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl() + "/user/" + testUser.getId(), List.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(2, response.getBody().size());
//
//        // Verify titles using JsonPath
//        String jsonResponse = restTemplate.getForObject(getBaseUrl() + "/user/" + testUser.getId(), String.class);
//        JSONArray titles = JsonPath.read(jsonResponse, "$[*].title");
//        assertTrue(titles.contains("User 1 Ad 1"));
//        assertTrue(titles.contains("User 1 Ad 2"));
//    }
}