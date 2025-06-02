package paint.projekt.sport_matcher.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  private String name;
  @JsonIgnore
  private String password;

  private String email;
  @Column(unique = true)
  private String username;
  private String role;
  private Boolean enabled; // in case we do account verification later


  @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}