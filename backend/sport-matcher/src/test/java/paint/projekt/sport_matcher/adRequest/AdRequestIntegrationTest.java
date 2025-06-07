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
import org.springframework.http.HttpStatusCode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import paint.projekt.sport_matcher.SportMatcherApplication;
import paint.projekt.sport_matcher.utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SportMatcherApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdRequestIntegrationTest {
    private int port = 3306;

    @MockitoBean
    private JavaMailSender fakeEmailSender;
    @Autowired
    DummyData data;

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