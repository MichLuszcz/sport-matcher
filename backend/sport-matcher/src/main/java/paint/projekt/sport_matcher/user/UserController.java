package paint.projekt.sport_matcher.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import paint.projekt.sport_matcher.security.UserPrincipal;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public UserDTO registerNewUser(@RequestBody RegisterRequest request) {
//        return addLinks(
        return userService.registerNewUser(request);
//        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        var users = userService.getAllUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long id) {
        userService.deleteUser(id, principal);
        return ResponseEntity.noContent().build();
    }

}
