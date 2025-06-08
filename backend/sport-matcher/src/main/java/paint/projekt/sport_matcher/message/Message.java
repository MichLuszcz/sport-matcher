package paint.projekt.sport_matcher.message;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import paint.projekt.sport_matcher.user.User;

import java.time.LocalDateTime;

/**
 * Encja reprezentująca wiadomość wysyłaną między użytkownikami.
 * Używana do komunikacji między użytkownikami w kontekście ogłoszeń.
 *
 * Pola wiadomości:
 * id – identyfikator wiadomości
 * sender – użytkownik, który wysłał wiadomość
 * receiver – użytkownik, który otrzymał wiadomość
 * content – treść wiadomości
 * sentAt – data i godzina wysłania wiadomości
 *
 * prePersist() - automatycznie ustawia datę i czas wysłania wiadomości na bieżący czas.
 */

@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  @ManyToOne(optional = false)
  @JoinColumn(name = "receiver_id", nullable = false)
  private User receiver;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name = "sent_at", nullable = false)
  private LocalDateTime sentAt;

  @PrePersist
  public void prePersist() {
    this.sentAt = LocalDateTime.now();
  }
}

