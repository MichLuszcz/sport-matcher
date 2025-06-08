package paint.projekt.sport_matcher.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Collection<Message> findAllBySenderId(Long senderId);

    Collection<Message> findAllByReceiverId(Long receiverId);
}