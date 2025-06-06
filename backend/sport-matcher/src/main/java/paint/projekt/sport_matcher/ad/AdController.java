package paint.projekt.sport_matcher.ad;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/ads")
public class AdController {

  private final AdService adService;

  @GetMapping
  public @ResponseBody List<AdDTO> getAllAds() {
    return adService.getAllAds();
  }

  @GetMapping("/{id}")
  public ResponseEntity<AdDTO> getAdById(@PathVariable Long id) {
    AdDTO adDTO = adService.getAdById(id);
    if (adDTO != null) {
      return ResponseEntity.ok(adDTO);
    }
    return ResponseEntity.notFound().build();
  }
}