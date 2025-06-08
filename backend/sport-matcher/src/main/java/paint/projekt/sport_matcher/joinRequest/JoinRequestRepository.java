package paint.projekt.sport_matcher.joinRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    Collection<JoinRequest> findAllByUserId(Long userId);
    Optional<JoinRequest> findJoinRequestByUser_IdAndAd_Id(Long userId, Long adId);

    Collection<JoinRequest> findAllByAdId(Long adId);
}