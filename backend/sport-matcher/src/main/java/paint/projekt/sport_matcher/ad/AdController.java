package paint.projekt.sport_matcher.ad;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import paint.projekt.sport_matcher.security.UserPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class AdController {

    private final AdService adService;

    @PostMapping("/ads")
    public ResponseEntity<AdDTO> createAd(@RequestBody AdCreationRequest adCreateRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        AdDTO createdAd = adService.createAd(adCreateRequest, userPrincipal);
        return new ResponseEntity<>(createdAd, HttpStatus.CREATED);
    }

    @GetMapping("/ads")
    public @ResponseBody List<AdDTO> getAllAds() {
        return adService.getAllAds();
    }

    @GetMapping("/ads/{id}")
    public ResponseEntity<AdDTO> getAdById(@PathVariable Long id) {
        AdDTO adDTO = adService.getAdById(id);
        if (adDTO != null) {
            return ResponseEntity.ok(adDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}/ads")
    public @ResponseBody List<AdDTO> getAdsByUserId(@PathVariable Long userId) {
        return adService.getAdsByUserId(userId);
    }

    @DeleteMapping("/ads/{id}")
    public ResponseEntity<AdDTO> deleteAd(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        adService.deleteAdById(id, userPrincipal);
        return ResponseEntity.noContent().build();
    }
}
