package paint.projekt.sport_matcher.adRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AdRequestRepository extends JpaRepository<AdRequest, Long> {
    Collection<AdRequest> findAllByUserId(Long userId);

    Collection<AdRequest> findAllByAdId(Long adId);
}