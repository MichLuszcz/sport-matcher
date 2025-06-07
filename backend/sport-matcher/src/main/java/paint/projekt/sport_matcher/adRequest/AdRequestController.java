package paint.projekt.sport_matcher.adRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/ad-requests")
public class AdRequestController {

    private final AdRequestService adRequestService;

    // todo verify user with principal
    @PostMapping
    public ResponseEntity<AdRequestDTO> createAdRequest(@RequestBody AdRequestCreationRequest adRequestCreationRequest) {
        AdRequestDTO createdAdRequest = adRequestService.createAdRequest(adRequestCreationRequest);
        return new ResponseEntity<>(createdAdRequest, HttpStatus.CREATED);
    }

    @GetMapping
    public @ResponseBody List<AdRequestDTO> getAllAdRequests() {
        return adRequestService.getAllAdRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdRequestDTO> getAdRequestById(@PathVariable Long id) {
        AdRequestDTO dto = adRequestService.getAdRequestById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public @ResponseBody List<AdRequestDTO> getAdRequestsByUserId(@PathVariable Long userId) {
        return adRequestService.getAdRequestsByUserId(userId);
    }

    @GetMapping("/ad/{adId}")
    public @ResponseBody List<AdRequestDTO> getAdRequestsByAdId(@PathVariable Long adId) {
        return adRequestService.getAdRequestsByAdId(adId);
    }
}
