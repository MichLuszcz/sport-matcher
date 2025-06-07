package paint.projekt.sport_matcher.JoinRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paint.projekt.sport_matcher.ad.Ad;
import paint.projekt.sport_matcher.ad.AdRepository;
import paint.projekt.sport_matcher.user.User;
import paint.projekt.sport_matcher.user.UserRepository;

import java.util.List;
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

    public JoinRequestDTO createJoinRequest(JoinRequestCreationRequest joinRequestCreationRequest) {
        Long userId = 1L;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Ad ad = adRepository.findById(joinRequestCreationRequest.adId())
                .orElseThrow(() -> new RuntimeException("Ad not found with id: " + joinRequestCreationRequest.adId()));

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
}