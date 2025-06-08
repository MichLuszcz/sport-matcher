package paint.projekt.sport_matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import paint.projekt.sport_matcher.config.AppConfiguration;
import paint.projekt.sport_matcher.security.JwtProperties;
import paint.projekt.sport_matcher.utils.DummyData;

@SpringBootApplication
@EnableConfigurationProperties(AppConfiguration.class)
public class SportMatcherApplication {

	private static final Logger log = LoggerFactory.getLogger(SportMatcherApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SportMatcherApplication.class, args);
	}

    @Bean
    public CommandLineRunner reportStatus(
            JwtProperties jwtProperties,
            AppConfiguration appConfiguration
    ) {
        return (args) -> {
            if (jwtProperties.getSecretKey() != null) {
                log.info("Loaded secret key for signing JWT tokens");
            } else {
                log.error("Missing JWT signing secret key");
            }

            log.info(String.format("Frontend app available on %s", appConfiguration.getWebsiteBaseUrl()));
            log.info(String.format("Schedule for deleting expired tokens (cron): %s", appConfiguration.getDeleteTokensCronExpression()));
        };
    }

	@Bean
    @Profile("dev")
    public CommandLineRunner addDummyData(DummyData generator) {
        return (args) -> {
            generator.addDummyData();
            log.info("Added dummy data to the database");
        };
    }
}
