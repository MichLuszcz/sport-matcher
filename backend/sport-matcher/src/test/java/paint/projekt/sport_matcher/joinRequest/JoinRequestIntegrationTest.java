package paint.projekt.sport_matcher.joinRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import paint.projekt.sport_matcher.SportMatcherApplication;
import paint.projekt.sport_matcher.utils.DummyData;
import org.testcontainers.junit.jupiter.Container;


import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(classes = SportMatcherApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class JoinRequestIntegrationTest {
    private int port;

    @MockitoBean
    private JavaMailSender fakeEmailSender;
    @Autowired
    DummyData data;


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

    @BeforeEach
    public void setupDatabase() {
        data.deleteAll();
        data.addDummyData();
//        var token = authService.attemptLogin(data.user_1.getUsername(), "password").getAccessToken();
//        headers.add("Authorization", "Bearer " + token);
    }

    @Test
    public void getAdRequestByIdExists() {
        var response = "aaa";
        assertEquals(response, "aaa");
    }
}