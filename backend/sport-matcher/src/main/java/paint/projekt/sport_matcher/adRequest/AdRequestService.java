package paint.projekt.sport_matcher.adRequest;

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
public class AdRequestService {
    private final AdRequestRepository adRequestRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;

    private AdRequestDTO convertToDto(AdRequest adRequest) {
        return AdRequestDTO.builder()
                .id(adRequest.getId())
                .userId(adRequest.getUser().getId())
                .adId(adRequest.getAd().getId())
//                .status(adRequest.getStatus())
                .createdAt(adRequest.getCreatedAt())
                .build();
    }

    public AdRequestDTO createAdRequest(AdRequestDTO adRequestDTO) {
        User user = userRepository.findById(adRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + adRequestDTO.getUserId()));

        Ad ad = adRepository.findById(adRequestDTO.getAdId())
                .orElseThrow(() -> new RuntimeException("Ad not found with id: " + adRequestDTO.getAdId()));

        AdRequest adRequest = new AdRequest();
        adRequest.setUser(user);
        adRequest.setAd(ad);
        // Status and createdAt are set by default in the entity

        AdRequest savedAdRequest = adRequestRepository.save(adRequest);
        return convertToDto(savedAdRequest);
    }

    public List<AdRequestDTO> getAllAdRequests() {
        return adRequestRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AdRequestDTO getAdRequestById(Long id) {
        return adRequestRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public List<AdRequestDTO> getAdRequestsByUserId(Long userId) {
        return adRequestRepository.findAllByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AdRequestDTO> getAdRequestsByAdId(Long adId) {
        return adRequestRepository.findAllByAdId(adId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}