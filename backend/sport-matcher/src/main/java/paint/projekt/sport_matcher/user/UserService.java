package paint.projekt.sport_matcher.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import paint.projekt.sport_matcher.exceptions.BadRequestException;
import paint.projekt.sport_matcher.exceptions.ForbiddenException;
import paint.projekt.sport_matcher.security.UserPrincipal;
// In a real application, you would use a password encoder
// import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final UserRole DEFAULT_ROLE = UserRole.USER;
    // private final PasswordEncoder passwordEncoder; // Inject password encoder

    private UserDTO convertToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .dateCreated(user.getDateCreated())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .isActive(user.getIsActive())
                .build();
    }

    private User expectUserExists(Long id) {
        var maybeUser = userRepository.findById(id);
        if (maybeUser.isEmpty()) {
            throw new UserNotFoundException("No user with id " + id);
        }
        return maybeUser.get();
    }

    public UserDTO registerNewUser(RegisterRequest request) {
        if (userRepository.existsUserByEmail(request.email())) {
            throw new UserRegistrationException("Email address is already being used");
        }

        if (userRepository.existsUserByUsername(request.username())) {
            throw new UserRegistrationException("Username is already being used");
        }

        var user = new User(request.username(), request.email(), passwordEncoder.encode(request.password()), request.name(), DEFAULT_ROLE, true);
        user = userRepository.save(user);
//        sendVerificationEmail(user);
        return convertToDto(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long id, UserPrincipal principal) {
        var maybeUser = userRepository.findById(id);
        if (maybeUser.isEmpty()){
            return;
        }
        var user = maybeUser.get();
        if (!principal.getUserId().equals(user.getId()) && !principal.isAdmin()) {
            throw new ForbiddenException("You are not permitted to delete this account");
        }

        userRepository.delete(user);
    }
}