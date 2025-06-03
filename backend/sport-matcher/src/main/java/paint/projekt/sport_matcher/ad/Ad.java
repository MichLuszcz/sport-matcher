package paint.projekt.sport_matcher.ad;

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