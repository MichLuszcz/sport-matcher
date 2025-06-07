package paint.projekt.sport_matcher.joinRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    Collection<JoinRequest> findAllByUserId(Long userId);

    Collection<JoinRequest> findAllByAdId(Long adId);
}