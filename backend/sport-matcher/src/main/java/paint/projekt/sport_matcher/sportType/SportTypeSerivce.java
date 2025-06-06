package paint.projekt.sport_matcher.sportType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SportTypeService {
    private final SportTypeRepository sportTypeRepository;

    private SportTypeDTO convertToDto(SportType sportType) {
        return SportTypeDTO.builder()
                .id(sportType.getId())
                .name(sportType.getName())
                .build();
    }

    public List<SportTypeDTO> getAllSportTypes() {
        return sportTypeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}