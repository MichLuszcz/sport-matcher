package paint.projekt.sport_matcher.joinRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/join-requests")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    // todo verify user with principal
    @PostMapping
    public ResponseEntity<JoinRequestDTO> createAdRequest(@RequestBody JoinRequestCreationRequest joinRequestCreationRequest) {
        JoinRequestDTO createdAdRequest = joinRequestService.createJoinRequest(joinRequestCreationRequest);
        return new ResponseEntity<>(createdAdRequest, HttpStatus.CREATED);
    }

    @GetMapping
    public @ResponseBody List<JoinRequestDTO> getAllAdRequests() {
        return joinRequestService.getAllJoinRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JoinRequestDTO> getAdRequestById(@PathVariable Long id) {
        JoinRequestDTO dto = joinRequestService.getJoinRequestById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public @ResponseBody List<JoinRequestDTO> getAdRequestsByUserId(@PathVariable Long userId) {
        return joinRequestService.getJoinRequestsByUserId(userId);
    }

    @GetMapping("/ad/{adId}")
    public @ResponseBody List<JoinRequestDTO> getAdRequestsByAdId(@PathVariable Long adId) {
        return joinRequestService.getJoinRequestsByAdId(adId);
    }
}
