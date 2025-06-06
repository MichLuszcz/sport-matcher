package paint.projekt.sport_matcher.adRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdRequestService {
    private final AdRequestRepository adRequestRepository;

    private AdRequestDTO convertToDto(AdRequest adRequest) {
        return AdRequestDTO.builder()
                .id(adRequest.getId())
                .userId(adRequest.getUser().getId())
                .adId(adRequest.getAd().getId())
                .status(adRequest.getStatus())
                .createdAt(adRequest.getCreatedAt())
                .build();
    }

    public List<AdRequestDTO> getAllAdRequests() {
        return adRequestRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}