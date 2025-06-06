package paint.projekt.sport_matcher.sportType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import paint.projekt.sport_matcher.ad.Ad;

import java.util.List;

/**
 * Encja reprezentująca typ sportu, którego dotyczy ogłoszenie.
 * Służy do klasyfikacji ogłoszeń według rodzaju aktywności fizycznej.
 *
 * Pola:
 * - id – identyfikator typu sportu
 * - name – pełna nazwa sportu
 * - ads – lista ogłoszeń, które dotyczą tego sportu (relacja 1:N, Jeden typ sportu do wielu ogłoszeń, ale ogłoszenie może mieć określony tylko jeden typ)
 */

@Entity
@Table(name = "sports_types")
@Getter
@Setter
public class SportType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String name;


  @OneToMany(mappedBy = "sportType")
  private List<Ad> ads;
}