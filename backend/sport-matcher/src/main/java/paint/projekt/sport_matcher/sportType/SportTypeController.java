package paint.projekt.sport_matcher.sportType;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/sport-types")
public class SportTypeController {

    private final SportTypeService sportTypeService;

    @GetMapping
    public @ResponseBody List<SportTypeDTO> getAllSportTypes() {
        return sportTypeService.getAllSportTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportTypeDTO> getSportTypeById(@PathVariable Long id) {
        SportTypeDTO dto = sportTypeService.getSportTypeById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<SportTypeDTO> getSportTypeByName(@PathVariable String name) {
        SportTypeDTO dto = sportTypeService.getSportTypeByName(name);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
}
