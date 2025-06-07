package paint.projekt.sport_matcher.ad;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import paint.projekt.sport_matcher.SportMatcherApplication;
import paint.projekt.sport_matcher.user.UserRepository;
import paint.projekt.sport_matcher.sportType.SportTypeRepository;
import paint.projekt.sport_matcher.utils.DummyData;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static paint.projekt.sport_matcher.utils.UrlBuilder.buildUrl;
@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SportMatcherApplication.class)
@DirtiesContext()
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

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("sport_matcher")
            .withUsername("myuser")
            .withPassword("secret");

    @DynamicPropertySource
    static void overrideDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void createAdSuccessfully() {

        AdCreationRequest request = new AdCreationRequest(1L, "ad1", "descr1", LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), "Warszawa", 2);

        var response = restTemplate.exchange(
                buildUrl("/api/ads/", port),
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                String.class
        );
        var json = JsonPath.parse(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Looking for a football partner", json.read("$.title"));
        assertEquals(data.sportType_football.getId(), json.read("$.sportId"));
        assertTrue(json.read("$.isActive"));
        assertNotNull(json.read("$.creationDateTime"));
    }

    @Test
    void getAllAds() {
        // football

        var response = restTemplate.exchange(
                buildUrl("/api/ads", port),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
        var json = JsonPath.parse(response.getBody());
        List<Map<String, Object>> ads = json.read("$");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, ads.size());

        assertTrue(ads.get(0).get("title").toString().contains("Ad1"));
//        assertTrue(titles.contains("Ad 1"));
//        assertTrue(titles.contains("Ad 2"));
    }

    @Test
    void getAdByIdExists() {

        var response = restTemplate.exchange(
        buildUrl("/api/ads/2" , port),
        HttpMethod.GET,
        new HttpEntity<>(headers),
        String.class
        );
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        var json = JsonPath.parse(response.getBody());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(data.ad_basketball_pickup.getId(), json.read("$.id"));
        assertEquals(data.ad_basketball_pickup.getTitle(), json.read("$.title"));
        assertEquals(data.ad_basketball_pickup.getLocation(), json.read("$.location"));
    }

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