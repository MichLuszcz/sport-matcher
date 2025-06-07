package paint.projekt.sport_matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import paint.projekt.sport_matcher.utils.DummyData;

@SpringBootApplication
public class SportMatcherApplication {

	private static final Logger log = LoggerFactory.getLogger(SportMatcherApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SportMatcherApplication.class, args);
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
