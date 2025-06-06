package paint.projekt.sport_matcher.ad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paint.projekt.sport_matcher.user.User;
import paint.projekt.sport_matcher.sportType.SportType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;

    private AdDTO convertToDto(Ad ad) {
        return AdDTO.builder()
                .id(ad.getId())
                .userId(ad.getUser().getId())
                .username(ad.getUser().getUsername())
                .sportTypeId(ad.getSportType() != null ? ad.getSportType().getId() : null)
                .sportTypeName(ad.getSportType() != null ? ad.getSportType().getName() : null)
                .title(ad.getTitle())
                .description(ad.getDescription())
                .dateStart(ad.getDateStart())
                .dateEnd(ad.getDateEnd())
                .timeStart(ad.getTimeStart())
                .timeEnd(ad.getTimeEnd())
                .location(ad.getLocation())
                .participants(ad.getParticipants())
                .isActive(ad.getIsActive())
                .creationDatetime(ad.getCreationDatetime())
                .build();
    }

    public List<AdDTO> getAllAds() {
        return adRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AdDTO getAdById(Long id) {
        return adRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null); // Or throw an exception
    }
}