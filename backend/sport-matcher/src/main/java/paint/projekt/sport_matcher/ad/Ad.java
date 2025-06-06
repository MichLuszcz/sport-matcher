package paint.projekt.sport_matcher.ad;
import paint.projekt.sport_matcher.sportType.SportType;
import paint.projekt.sport_matcher.user.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Encja reprezentująca ogłoszenie szukania partnera do wydarzenia.
 * Używana do wyświetlania i tworzenia ogłoszeń.
 *
 * Ogłoszenie zawiera:
 * id - identyfikator ogłoszenia
 * user - Użytkownik który utworzył ogłoszenie (obiekt User)
 * sportType - typ sportu, ktorego dotyczy wydarzenie
 * title - tytuł ogłoszenia
 * description - dodatkowy opis ogłoszenia
 * dateStart - data rozpoczęcia wydarzenia (nie może być w przeszłości)
 * dateEnd - data zakończenia wydarzenia (nie może być wcześniejsza niż dateStart
 * timeStart - godzina rozpoczęcia (nie może być w przeszłości)
 * timeEnd - godzina zakończenia (nie może być wcześniejsza niż timeStart)
 * location - lokalizacja wydarzenia
 * participants - liczba poszukiwanych uczestników
 * isActive - określa czy ogłoszenie jest aktywne, czy nie (znaleziono uczestników, usunieto ogłoszenie itp.)
 * creationDatetime - data i czas utworzenia ogłoszenia, określane przez prePersist().
 *
 * prePersist() - metoda która automatycznie ustawia datę utworzenia ogłoszenia na aktualną datę przy zapisie do bazy danych
 */

@Entity
@Table(name = "ads")
@Getter
@Setter
public class Ad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "sport_type_id")
  private SportType sportType;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "date_start")
  private LocalDate dateStart;

  @Column(name = "date_end")
  private LocalDate dateEnd;

  @Column(name = "time_start")
  private LocalTime timeStart;

  @Column(name = "time_end")
  private LocalTime timeEnd;

  @Column(length = 200)
  private String location;

  private Integer participants;

  @Column(name = "is_active")
  private Boolean isActive = true;

  @Column(name = "creation_datetime")
  private LocalDateTime creationDatetime;

  @PrePersist
  public void prePersist() {
    this.creationDatetime = LocalDateTime.now();
  }
}