package paint.projekt.sport_matcher.adRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRequestRepository extends JpaRepository<AdRequest, Long> {
}