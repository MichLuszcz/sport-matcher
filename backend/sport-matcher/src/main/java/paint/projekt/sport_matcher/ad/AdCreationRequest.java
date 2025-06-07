package paint.projekt.sport_matcher.ad;

import java.time.LocalDate;
import java.time.LocalTime;

public record AdCreationRequest(Long sportTypeId, String title, String description, LocalDate dateStart,
                                LocalDate dateEnd, LocalTime timeStart, LocalTime timeEnd, String location,
                                Integer participants) {
}
