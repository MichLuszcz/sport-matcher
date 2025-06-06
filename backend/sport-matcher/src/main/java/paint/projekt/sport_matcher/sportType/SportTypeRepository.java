package paint.projekt.sport_matcher.sportType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SportTypeRepository extends JpaRepository<SportType, Long> {
    Optional<SportType> findByName(String name);
}