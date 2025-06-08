package paint.projekt.sport_matcher.sportType;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Specified sport type does not exist")
public class SportTypeNotFound extends RuntimeException {
    public SportTypeNotFound(String message) {
        super(message);
    }
}
