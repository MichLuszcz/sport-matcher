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
@RequestMapping(path = "/api/join-requests")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    //maybe change to path variable
    @PostMapping
    public ResponseEntity<JoinRequestDTO> createAdRequest(@RequestBody JoinRequestCreationRequest joinRequestCreationRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        JoinRequestDTO createdAdRequest = joinRequestService.createJoinRequest(joinRequestCreationRequest, userPrincipal);
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

    @GetMapping("/users/{userId}")
    public @ResponseBody List<JoinRequestDTO> getAdRequestsByUserId(@PathVariable Long userId) {
        return joinRequestService.getJoinRequestsByUserId(userId);
    }

    @GetMapping("/ads/{adId}")
    public @ResponseBody List<JoinRequestDTO> getAdRequestsByAdId(@PathVariable Long adId) {
        return joinRequestService.getJoinRequestsByAdId(adId);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<JoinRequestDTO> acceptJoinRequest(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        var accepted_request = joinRequestService.alterJoinRequestStatus(id, userPrincipal, RequestStatus.ACCEPTED);
        return ResponseEntity.ok(accepted_request);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<JoinRequestDTO> rejectJoinRequest(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        var rejected_request = joinRequestService.alterJoinRequestStatus(id, userPrincipal, RequestStatus.REJECTED);
        return ResponseEntity.ok(rejected_request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JoinRequestDTO> deleteJoinRequest(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        joinRequestService.deleteJoinRequest(id, userPrincipal);
        return ResponseEntity.ok().build();
    }

}
