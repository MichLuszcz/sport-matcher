package paint.projekt.sport_matcher.joinRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paint.projekt.sport_matcher.ad.Ad;
import paint.projekt.sport_matcher.ad.AdRepository;
import paint.projekt.sport_matcher.exceptions.BadRequestException;
import paint.projekt.sport_matcher.exceptions.ForbiddenException;
import paint.projekt.sport_matcher.exceptions.NotFoundException;
import paint.projekt.sport_matcher.security.UserPrincipal;
import paint.projekt.sport_matcher.user.User;
import paint.projekt.sport_matcher.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;

    private JoinRequestDTO convertToDto(JoinRequest adRequest) {
        return JoinRequestDTO.builder()
                .id(adRequest.getId())
                .userId(adRequest.getUser().getId())
                .adId(adRequest.getAd().getId())
                .status(adRequest.getStatus())
                .createdAt(adRequest.getCreatedAt())
                .build();
    }

    private JoinRequest expectJoinRequestExists(Long id) {
        Optional<JoinRequest> maybeJoinRequest = joinRequestRepository.findById(id);
        if (maybeJoinRequest.isEmpty()) {
            throw new NotFoundException("Join request not found with id: " + id);
        }
        return maybeJoinRequest.get();
    }

    public JoinRequestDTO createJoinRequest(JoinRequestCreationRequest joinRequestCreationRequest, UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Ad ad = adRepository.findById(joinRequestCreationRequest.adId())
                .orElseThrow(() -> new RuntimeException("Ad not found with id: " + joinRequestCreationRequest.adId()));
        //todo check for duplicate from the user on the ad
        // todo check if requesting user is not OP
        JoinRequest adRequest = new JoinRequest();
        adRequest.setUser(user);
        adRequest.setAd(ad);
        // Status and createdAt are set by default in the entity

        JoinRequest savedJoinRequest = joinRequestRepository.save(adRequest);
        return convertToDto(savedJoinRequest);
    }

    public List<JoinRequestDTO> getAllJoinRequests() {
        return joinRequestRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public JoinRequestDTO getJoinRequestById(Long id) {
        return joinRequestRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public List<JoinRequestDTO> getJoinRequestsByUserId(Long userId) {
        return joinRequestRepository.findAllByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<JoinRequestDTO> getJoinRequestsByAdId(Long adId) {
        return joinRequestRepository.findAllByAdId(adId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Accept a join request to one of the ads posted by the user sending the accept request.
     *
     * @param id            JoinRequest Id
     * @param userPrincipal logged in user data
     * @return the request after alteration
     */
    public JoinRequestDTO alterJoinRequestStatus(Long id, UserPrincipal userPrincipal, RequestStatus requestStatus) {

        var joinRequest = expectJoinRequestExists(id);
        if (!joinRequest.getAd().getUser().getId().equals(userPrincipal.getUserId())) {
            throw new ForbiddenException("Cannot alter request to other user's ad");
        }
        if (joinRequest.getStatus() != RequestStatus.PENDING) {
            throw new BadRequestException("Cannot alter state of non-pending request");
        }
        joinRequest.setStatus(requestStatus);
        joinRequestRepository.save(joinRequest);
        return convertToDto(joinRequest);
    }

    public void deleteJoinRequest(Long id, UserPrincipal userPrincipal) {
        // permitted for: admin, the user who created the request
        Optional<JoinRequest> maybeJoinRequest = joinRequestRepository.findById(id);
        if (maybeJoinRequest.isEmpty()) {
            return;
        }
        var joinRequest = maybeJoinRequest.get();
        if (!joinRequest.getUser().getId().equals(userPrincipal.getUserId()) && !userPrincipal.isAdmin()) {
            throw new ForbiddenException("Cannot delete other user's request");
        }


        joinRequestRepository.delete(joinRequest);

    }
}