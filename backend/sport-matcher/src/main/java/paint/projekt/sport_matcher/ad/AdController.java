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
@RequestMapping(path = "/api/ads")
public class AdController {

    private final AdService adService;

    @PostMapping
    public ResponseEntity<AdDTO> createAd(@RequestBody AdCreationRequest adCreateRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        AdDTO createdAd = adService.createAd(adCreateRequest, userPrincipal);
        return new ResponseEntity<>(createdAd, HttpStatus.CREATED);
    }

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

    @GetMapping("/user/{userId}")
    public @ResponseBody List<AdDTO> getAdsByUserId(@PathVariable Long userId) {
        return adService.getAdsByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdDTO> deleteAdById(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        adService.deleteAdById(id, userPrincipal);
        return ResponseEntity.noContent().build();
    }
}
