package paint.projekt.sport_matcher.sportType;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/sport-types")
public class SportTypeController {

  private final SportTypeService sportTypeService;

  @GetMapping
  public @ResponseBody List<SportTypeDTO> getAllSportTypes() {
    return sportTypeService.getAllSportTypes();
  }
}