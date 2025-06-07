package paint.projekt.sport_matcher.utils;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import paint.projekt.sport_matcher.JoinRequest.JoinRequest;
import paint.projekt.sport_matcher.ad.Ad;
import paint.projekt.sport_matcher.ad.AdRepository;
import paint.projekt.sport_matcher.JoinRequest.JoinRequestRepository;
import paint.projekt.sport_matcher.JoinRequest.RequestStatus;
import paint.projekt.sport_matcher.message.Message;
import paint.projekt.sport_matcher.message.MessageRepository;
import paint.projekt.sport_matcher.sportType.SportType;
import paint.projekt.sport_matcher.sportType.SportTypeRepository;
import paint.projekt.sport_matcher.user.User;
import paint.projekt.sport_matcher.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Utility for creating and deleting dummy data for testing and demonstration purposes.
 * This class populates the database with sample users, sport types, ads, ad requests, and messages.
 */
@Service
@RequiredArgsConstructor
public class DummyData {

    // Repositories for interacting with the database
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final AdRepository adRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final MessageRepository messageRepository;
    private final SportTypeRepository sportTypeRepository;

    // Public fields to hold references to the created dummy data entities
    public User user_john;
    public User user_jane;
    public User user_mike;
    public User admin_alice;

    public SportType sportType_football;
    public SportType sportType_basketball;
    public SportType sportType_tennis;

    public Ad ad_football_match;
    public Ad ad_basketball_pickup;
    public Ad ad_tennis_partner;
    public Ad ad_inactive_ad;

    public JoinRequest joinRequest_john_to_football;
    public JoinRequest joinRequest_jane_to_football_accepted;
    public JoinRequest joinRequest_mike_to_basketball_rejected;

    public Message message_john_to_jane;
    public Message message_jane_to_john;
    public Message message_mike_to_admin;

    /**
     * Populates the database with dummy data.
     * This method creates users, sport types, ads, ad requests, and messages
     * and saves them to their respective repositories.
     */
    public void addDummyData() {
        // 1. Create and save User entities
        user_john = new User();
        user_john.setUsername("john_doe");
        user_john.setEmail("john.doe@example.com");
        user_john.setPassword("password123");
        user_john.setName("John Doe");
        user_john.setRole("USER");
        user_john.setIsActive(true);
        user_john = userRepository.save(user_john);

        user_jane = new User();
        user_jane.setUsername("jane_smith");
        user_jane.setEmail("jane.smith@example.com");
        user_jane.setPassword("securepass");
        user_jane.setName("Jane Smith");
        user_jane.setRole("USER");
        user_jane.setIsActive(true);
        user_jane = userRepository.save(user_jane);

        user_mike = new User();
        user_mike.setUsername("mike_wazowski");
        user_mike.setEmail("mike.wazowski@example.com");
        user_mike.setPassword("mike123");
        user_mike.setName("Mike Wazowski");
        user_mike.setRole("USER");
        user_mike.setIsActive(true);
        user_mike = userRepository.save(user_mike);

        admin_alice = new User();
        admin_alice.setUsername("admin_alice");
        admin_alice.setEmail("admin.alice@example.com");
        admin_alice.setPassword("adminpass");
        admin_alice.setName("Alice Admin");
        admin_alice.setRole("ADMIN");
        admin_alice.setIsActive(true);
        admin_alice = userRepository.save(admin_alice);

        // 2. Create and save SportType entities
        sportType_football = new SportType();
        sportType_football.setName("Football");
        sportType_football = sportTypeRepository.save(sportType_football);

        sportType_basketball = new SportType();
        sportType_basketball.setName("Basketball");
        sportType_basketball = sportTypeRepository.save(sportType_basketball);

        sportType_tennis = new SportType();
        sportType_tennis.setName("Tennis");
        sportType_tennis = sportTypeRepository.save(sportType_tennis);

        // 3. Create and save Ad entities
        ad_football_match = new Ad();
        ad_football_match.setUser(user_john); // John creates a football ad
        ad_football_match.setSportType(sportType_football);
        ad_football_match.setTitle("5-a-side Football Match Needed!");
        ad_football_match.setDescription("Looking for players for a friendly 5-a-side football match this weekend. All skill levels welcome!");
        ad_football_match.setDateStart(LocalDate.now().plusDays(2)); // 2 days from now
        ad_football_match.setDateEnd(LocalDate.now().plusDays(2));
        ad_football_match.setTimeStart(LocalTime.of(18, 0)); // 6:00 PM
        ad_football_match.setTimeEnd(LocalTime.of(20, 0)); // 8:00 PM
        ad_football_match.setLocation("Local Park Pitch");
        ad_football_match.setParticipants(8);
        ad_football_match.setIsActive(true);
        ad_football_match = adRepository.save(ad_football_match);

        ad_basketball_pickup = new Ad();
        ad_basketball_pickup.setUser(user_jane); // Jane creates a basketball ad
        ad_basketball_pickup.setSportType(sportType_basketball);
        ad_basketball_pickup.setTitle("Pickup Basketball Game - City Courts");
        ad_basketball_pickup.setDescription("Casual pickup game at City Courts. Bring your A-game!");
        ad_basketball_pickup.setDateStart(LocalDate.now().plusDays(1)); // 1 day from now
        ad_basketball_pickup.setDateEnd(LocalDate.now().plusDays(1));
        ad_basketball_pickup.setTimeStart(LocalTime.of(16, 30)); // 4:30 PM
        ad_basketball_pickup.setTimeEnd(LocalTime.of(17, 30)); // 5:30 PM
        ad_basketball_pickup.setLocation("City Basketball Courts");
        ad_basketball_pickup.setParticipants(4);
        ad_basketball_pickup.setIsActive(true);
        ad_basketball_pickup = adRepository.save(ad_basketball_pickup);

        ad_tennis_partner = new Ad();
        ad_tennis_partner.setUser(user_mike); // Mike creates a tennis ad
        ad_tennis_partner.setSportType(sportType_tennis);
        ad_tennis_partner.setTitle("Tennis Partner Wanted - Intermediate Level");
        ad_tennis_partner.setDescription("Looking for a tennis partner for regular practice. Intermediate level preferred. Available weekday evenings.");
        ad_tennis_partner.setDateStart(LocalDate.now().plusDays(5)); // 5 days from now
        ad_tennis_partner.setDateEnd(LocalDate.now().plusDays(5));
        ad_tennis_partner.setTimeStart(LocalTime.of(19, 0)); // 7:00 PM
        ad_tennis_partner.setTimeEnd(LocalTime.of(20, 30)); // 8:30 PM
        ad_tennis_partner.setLocation("Community Tennis Courts");
        ad_tennis_partner.setParticipants(1);
        ad_tennis_partner.setIsActive(true);
        ad_tennis_partner = adRepository.save(ad_tennis_partner);

        ad_inactive_ad = new Ad();
        ad_inactive_ad.setUser(user_john);
        ad_inactive_ad.setSportType(sportType_football);
        ad_inactive_ad.setTitle("Old Football Ad (Inactive)");
        ad_inactive_ad.setDescription("This ad is no longer active as participants have been found.");
        ad_inactive_ad.setDateStart(LocalDate.now().minusDays(10)); // In the past
        ad_inactive_ad.setDateEnd(LocalDate.now().minusDays(10));
        ad_inactive_ad.setTimeStart(LocalTime.of(10, 0));
        ad_inactive_ad.setTimeEnd(LocalTime.of(11, 0));
        ad_inactive_ad.setLocation("Past Event Location");
        ad_inactive_ad.setParticipants(10);
        ad_inactive_ad.setIsActive(false); // Set to inactive
        ad_inactive_ad = adRepository.save(ad_inactive_ad);

        // 4. Create and save AdRequest entities
        joinRequest_john_to_football = new JoinRequest();
        joinRequest_john_to_football.setUser(user_mike); // Mike applies to John's football ad
        joinRequest_john_to_football.setAd(ad_football_match);
        joinRequest_john_to_football.setStatus(RequestStatus.PENDING); // Default status is PENDING, but explicitly setting
        joinRequest_john_to_football = joinRequestRepository.save(joinRequest_john_to_football);

        joinRequest_jane_to_football_accepted = new JoinRequest();
        joinRequest_jane_to_football_accepted.setUser(user_jane); // Jane applies to John's football ad
        joinRequest_jane_to_football_accepted.setAd(ad_football_match);
        joinRequest_jane_to_football_accepted.setStatus(RequestStatus.ACCEPTED); // Accepted status
        joinRequest_jane_to_football_accepted = joinRequestRepository.save(joinRequest_jane_to_football_accepted);

        joinRequest_mike_to_basketball_rejected = new JoinRequest();
        joinRequest_mike_to_basketball_rejected.setUser(user_john); // John applies to Jane's basketball ad
        joinRequest_mike_to_basketball_rejected.setAd(ad_basketball_pickup);
        joinRequest_mike_to_basketball_rejected.setStatus(RequestStatus.REJECTED); // Rejected status
        joinRequest_mike_to_basketball_rejected = joinRequestRepository.save(joinRequest_mike_to_basketball_rejected);

        // 5. Create and save Message entities
        message_john_to_jane = new Message();
        message_john_to_jane.setSender(user_john);
        message_john_to_jane.setReceiver(user_jane);
        message_john_to_jane.setContent("Hey Jane, are you still looking for players for the basketball game?");
        message_john_to_jane = messageRepository.save(message_john_to_jane);

        message_jane_to_john = new Message();
        message_jane_to_john.setSender(user_jane);
        message_jane_to_john.setReceiver(user_john);
        message_jane_to_john.setContent("Yes, we still need one more! Are you interested?");
        message_jane_to_john = messageRepository.save(message_jane_to_john);

        message_mike_to_admin = new Message();
        message_mike_to_admin.setSender(user_mike);
        message_mike_to_admin.setReceiver(admin_alice);
        message_mike_to_admin.setContent("Hello Admin, I have a question about my recent ad for tennis partner.");
        message_mike_to_admin = messageRepository.save(message_mike_to_admin);
    }

    /**
     * Deletes all dummy data created by this class from the database.
     * The order of deletion is important due to foreign key constraints.
     */
    public void deleteAll() {
        // Delete dependent entities first to avoid foreign key constraint violations
        messageRepository.deleteAll();
        joinRequestRepository.deleteAll();
        adRepository.deleteAll();
        sportTypeRepository.deleteAll();
        userRepository.deleteAll();
    }
}
