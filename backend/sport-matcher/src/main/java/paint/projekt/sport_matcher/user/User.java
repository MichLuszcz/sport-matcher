package paint.projekt.sport_matcher.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import paint.projekt.sport_matcher.ad.Ad;
import paint.projekt.sport_matcher.message.Message;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Encja reprezentująca konto użytkownika aplikacji.
 *
 * Pola użytkownika:
 * id – identyfikator użytkownika
 * username – unikalna nazwa użytkownika
 * email – unikaly email potrzebny do założenia konta
 * password – hasło do konta
 * name – imie użytkownika
 * dateCreated - data utworzenia konta
 * isActive - status aktywności konta (true - istnieje, false - nie, np usunięte)
 * role - rola użytkownika (USER/ADMIN)
 *
 * prePersist() - automatycznie ustawia datę i czas utworzenia konta na bieżący czas.
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"password", "ads", "sentMessages", "receivedMessages"})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 15, nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime dateCreated;

  @Column
  private Boolean isActive = true;

  private String role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Ad> ads;

  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
  private List<Message> sentMessages;

  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
  private List<Message> receivedMessages;


  @PrePersist
  public void prePersist() {
    this.dateCreated = LocalDateTime.now();
  }
}