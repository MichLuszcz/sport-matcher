package paint.projekt.sport_matcher.ad;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.OpaqueTokenDsl;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Service;
import paint.projekt.sport_matcher.exceptions.ForbiddenException;
import paint.projekt.sport_matcher.security.UserPrincipal;
import paint.projekt.sport_matcher.sportType.SportType;
import paint.projekt.sport_matcher.sportType.SportTypeRepository;
import paint.projekt.sport_matcher.user.User;
import paint.projekt.sport_matcher.user.UserNotFoundException;
import paint.projekt.sport_matcher.user.UserRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
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
                .creationDateTime(ad.getCreationDatetime())
                .build();
    }

    // Method to create a new Ad using AdDTO
    public AdDTO createAd(AdCreationRequest adCreationRequest, UserPrincipal userPrincipal) {
        // todo get user by principal
        System.out.println("entered createAd");
        User user = userRepository.findById(userPrincipal.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userPrincipal.getUserId()));

        SportType sportType = sportTypeRepository.findById(adCreationRequest.sportTypeId())
                .orElseThrow(() -> new RuntimeException("SportType not found with id: " + adCreationRequest.sportTypeId()));

        Ad ad = new Ad();
        ad.setUser(user);
        ad.setSportType(sportType);
        ad.setTitle(adCreationRequest.title());
        ad.setDescription(adCreationRequest.description());
        ad.setDateStart(adCreationRequest.dateStart());
        ad.setDateEnd(adCreationRequest.dateEnd());
        ad.setTimeStart(adCreationRequest.timeStart());
        ad.setTimeEnd(adCreationRequest.timeEnd());
        ad.setLocation(adCreationRequest.location());
        ad.setParticipants(adCreationRequest.participants());
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

    public void deleteAdById(Long id, UserPrincipal userPrincipal) {
        Optional<Ad> maybeAd = adRepository.findById(id);
        if (maybeAd.isEmpty()){
            return;
        }
        Ad ad = maybeAd.get();
        Optional<User> maybeUser =  userRepository.findById(userPrincipal.getUserId());
        if (maybeUser.isEmpty()) {
            return;
        }
        User user = maybeUser.get();

        if (!user.getId().equals(ad.getUser().getId()) && !userPrincipal.isAdmin()) {
            throw (new ForbiddenException("You are not permitted to delete this ad"));
        }

        adRepository.delete(ad);
    }
}