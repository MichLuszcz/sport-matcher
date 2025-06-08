package paint.projekt.sport_matcher.joinRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JoinRequestDTO extends RepresentationModel<JoinRequestDTO> {
    private Long id;
    private Long userId;
    private Long adId;
    private RequestStatus status;
    private LocalDateTime createdAt;
}