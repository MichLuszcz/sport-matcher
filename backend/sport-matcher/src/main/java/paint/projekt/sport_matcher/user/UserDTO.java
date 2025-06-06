package paint.projekt.sport_matcher.user;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {
    private Long id;
    private String username;
    private String email;
    private String name;
    private LocalDateTime dateCreated;
    private Boolean isActive;
    private String role;
}