package paint.projekt.sport_matcher.joinRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import paint.projekt.sport_matcher.security.UserPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    //maybe change to path variable
    @PostMapping("/ads/{adId}/join-requests")
    public ResponseEntity<JoinRequestDTO> createJoinRequest(@PathVariable Long adId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        JoinRequestDTO createdAdRequest = joinRequestService.createJoinRequest(adId, userPrincipal);
        return new ResponseEntity<>(createdAdRequest, HttpStatus.CREATED);
    }

    @GetMapping("/join-requests")
    public @ResponseBody List<JoinRequestDTO> getAllJoinRequests() {
        return joinRequestService.getAllJoinRequests();
    }

    @GetMapping("/join-requests/{id}")
    public ResponseEntity<JoinRequestDTO> getJoinRequest(@PathVariable Long id) {
        JoinRequestDTO dto = joinRequestService.getJoinRequestById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/users/{userId}/join-requests")
    public @ResponseBody List<JoinRequestDTO> getUserJoinRequests(@PathVariable Long userId) {
        return joinRequestService.getJoinRequestsByUserId(userId);
    }

    @GetMapping("/ads/{adId}/join-requests")
    public @ResponseBody List<JoinRequestDTO> getAdJoinRequests(@PathVariable Long adId) {
        return joinRequestService.getJoinRequestsByAdId(adId);
    }

    @PutMapping("/join-requests/{id}/accept")
    public ResponseEntity<JoinRequestDTO> acceptJoinRequest(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        var accepted_request = joinRequestService.alterJoinRequestStatus(id, userPrincipal, RequestStatus.ACCEPTED);
        return ResponseEntity.ok(accepted_request);
    }

    @PutMapping("/join-requests/{id}/reject")
    public ResponseEntity<JoinRequestDTO> rejectJoinRequest(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        var rejected_request = joinRequestService.alterJoinRequestStatus(id, userPrincipal, RequestStatus.REJECTED);
        return ResponseEntity.ok(rejected_request);
    }

    @DeleteMapping("/join-requests/{id}")
    public ResponseEntity<JoinRequestDTO> deleteJoinRequest(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        joinRequestService.deleteJoinRequest(id, userPrincipal);
        return ResponseEntity.ok().build();
    }

}
