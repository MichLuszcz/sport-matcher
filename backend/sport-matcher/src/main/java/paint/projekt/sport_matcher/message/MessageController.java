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
@RequestMapping(path = "/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageCreationRequest messageCreationRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        MessageDTO createdMessage = messageService.createMessage(messageCreationRequest, userPrincipal);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @GetMapping
    public @ResponseBody List<MessageDTO> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable Long id) {
        MessageDTO dto = messageService.getMessageById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/sender/{senderId}")
    public @ResponseBody List<MessageDTO> getMessagesBySenderId(@PathVariable Long senderId) {
        return messageService.getMessagesBySenderId(senderId);
    }

    @GetMapping("/receiver/{receiverId}")
    public @ResponseBody List<MessageDTO> getMessagesByReceiverId(@PathVariable Long receiverId) {
        return messageService.getMessagesByReceiverId(receiverId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO> deleteMessage(@PathVariable Long id,  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        messageService.deleteMessage(id, userPrincipal);
        return ResponseEntity.ok().build();
    }
}