package paint.projekt.sport_matcher;

import org.springframework.boot.SpringApplication;

public class TestSportMatcherApplication {

	public static void main(String[] args) {
		SpringApplication.from(SportMatcherApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
