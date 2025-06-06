package paint.projekt.sport_matcher.ad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called adRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
}
