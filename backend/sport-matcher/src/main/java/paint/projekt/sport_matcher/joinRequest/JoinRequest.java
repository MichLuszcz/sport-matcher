package paint.projekt.sport_matcher.joinRequest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import paint.projekt.sport_matcher.ad.Ad;
import paint.projekt.sport_matcher.user.User;

import java.time.LocalDateTime;

/**
 * Encja reprezentująca zgłoszenie użytkownika do wydarzenia z ogłoszenia.
 * Używana gdy użytkownik chce dołączyć do wydarzenia z ogłoszenia.
 * <p>
 * Pola zgłoszenia:
 * id – identyfikator zgłoszenia
 * user – użytkownik, który wysłał zgłoszenie
 * ad – ogłoszenie do którego użytkownik się zgłasza
 * status – status zgłoszenia (wartości z enumu RequestStatus: PENDING, ACCEPTED, REJECTED)
 * createdAt – data i czas wysłania zgłoszenia
 * <p>
 * prePersist() - metoda która automatycznie ustawia datę wysłania prośby o dołączenie do wydarzenia na aktualną datę przy zapisie do bazy danych
 */

@Entity
@Table(name = "join_requests")
@Getter
@Setter
public class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}