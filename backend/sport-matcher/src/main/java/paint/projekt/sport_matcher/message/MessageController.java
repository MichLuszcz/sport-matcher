package paint.projekt.sport_matcher.message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/messages")
public class MessageController {

  private final MessageService messageService;

  @GetMapping
  public @ResponseBody List<MessageDTO> getAllMessages() {
    return messageService.getAllMessages();
  }
}