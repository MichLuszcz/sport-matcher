package paint.projekt.sport_matcher.ad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    // Method to create a new Ad
    public AdDTO createAd(AdCreateDTO createDTO) {
        User user = userRepository.findById(createDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + createDTO.getUserId()));

        SportType sportType = sportTypeRepository.findById(createDTO.getSportTypeId())
                .orElseThrow(() -> new RuntimeException("SportType not found with id: " + createDTO.getSportTypeId()));

        Ad ad = new Ad();
        ad.setUser(user);
        ad.setSportType(sportType);
        ad.setTitle(createDTO.getTitle());
        ad.setDescription(createDTO.getDescription());
        ad.setDateStart(createDTO.getDateStart());
        ad.setDateEnd(createDTO.getDateEnd());
        ad.setTimeStart(createDTO.getTimeStart());
        ad.setTimeEnd(createDTO.getTimeEnd());
        ad.setLocation(createDTO.getLocation());
        ad.setParticipants(createDTO.getParticipants());

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