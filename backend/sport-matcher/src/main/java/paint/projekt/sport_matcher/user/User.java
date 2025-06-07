package paint.projekt.sport_matcher.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
 * role - rola użytkownika (ROLE_USER/ROLE_ADMIN)
 *
 * prePersist() - automatycznie ustawia datę i czas utworzenia konta na bieżący czas.
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"password", "ads", "sentMessages", "receivedMessages"})
@NoArgsConstructor
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
  private Boolean isActive;

  private String role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Ad> ads;

  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
  private List<Message> sentMessages;

  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
  private List<Message> receivedMessages;

  public User(String username, String email, String password, String name, String role, Boolean isActive) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }


  @PrePersist
  public void prePersist() {
    this.dateCreated = LocalDateTime.now();
  }
}