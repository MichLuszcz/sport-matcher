package paint.projekt.sport_matcher.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import paint.projekt.sport_matcher.security.UserPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageCreationRequest messageCreationRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        MessageDTO createdMessage = messageService.createMessage(messageCreationRequest, userPrincipal);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable Long id) {
        MessageDTO dto = messageService.getMessageById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/users/{userId}/messages/sent")
    public @ResponseBody List<MessageDTO> getMessagesBySenderId(@PathVariable Long userId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return messageService.getMessagesBySenderId(userId, userPrincipal);
    }

    @GetMapping("/users/{userId}/messages/received")
    public @ResponseBody List<MessageDTO> getMessagesByReceiverId(@PathVariable Long userId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return messageService.getMessagesByReceiverId(userId, userPrincipal);
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<MessageDTO> deleteMessage(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageService.deleteMessage(id, userPrincipal);
        return ResponseEntity.ok().build();
    }
}