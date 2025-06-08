package paint.projekt.sport_matcher.ad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AdDTO extends RepresentationModel<AdDTO> {
    private Long id;
    private Long userId;
    private String username; // To show the username directly
    private Long sportTypeId;
    private String sportTypeName; // To show the sport type name directly
    private String title;
    private String description;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String location;
    private Integer participants;
    private Boolean isActive;
    private LocalDateTime creationDateTime;
}