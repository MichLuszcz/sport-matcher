package paint.projekt.sport_matcher.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Getter
@Setter
@EnableScheduling
@Configuration
@ConfigurationProperties("app")
public class AppConfiguration {
    private String deleteTokensCronExpression;
    private String websiteBaseUrl = "http://localhost:5173";
//    private String confirmEmailUrl;
//    private String resetPasswordUrl;
}
