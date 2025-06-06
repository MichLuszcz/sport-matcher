package paint.projekt.sport_matcher.ad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paint.projekt.sport_matcher.sportType.SportType;
import paint.projekt.sport_matcher.sportType.SportTypeRepository;
import paint.projekt.sport_matcher.user.User;
import paint.projekt.sport_matcher.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final SportTypeRepository sportTypeRepository;

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

    // Method to create a new Ad using AdDTO
    public AdDTO createAd(AdDTO adDTO) {
        User user = userRepository.findById(adDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + adDTO.getUserId()));

        SportType sportType = sportTypeRepository.findById(adDTO.getSportTypeId())
                .orElseThrow(() -> new RuntimeException("SportType not found with id: " + adDTO.getSportTypeId()));

        Ad ad = new Ad();
        ad.setUser(user);
        ad.setSportType(sportType);
        ad.setTitle(adDTO.getTitle());
        ad.setDescription(adDTO.getDescription());
        ad.setDateStart(adDTO.getDateStart());
        ad.setDateEnd(adDTO.getDateEnd());
        ad.setTimeStart(adDTO.getTimeStart());
        ad.setTimeEnd(adDTO.getTimeEnd());
        ad.setLocation(adDTO.getLocation());
        ad.setParticipants(adDTO.getParticipants());
        // isActive and creationDatetime are set by default in the entity

        Ad savedAd = adRepository.save(ad);
        return convertToDto(savedAd);
    }

    public List<AdDTO> getAllAds() {
        return adRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AdDTO getAdById(Long id) {
        return adRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public List<AdDTO> getAdsByUserId(Long userId) {
        return adRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}