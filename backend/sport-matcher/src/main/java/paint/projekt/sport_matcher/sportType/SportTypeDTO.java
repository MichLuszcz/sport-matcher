package paint.projekt.sport_matcher.sportType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SportTypeDTO extends RepresentationModel<SportTypeDTO> {
    private Long id;
    private String name;
}