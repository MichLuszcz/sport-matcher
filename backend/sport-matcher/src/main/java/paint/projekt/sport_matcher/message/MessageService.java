package paint.projekt.sport_matcher.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paint.projekt.sport_matcher.security.UserPrincipal;
import paint.projekt.sport_matcher.user.User;
import paint.projekt.sport_matcher.user.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private MessageDTO convertToDto(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .receiverId(message.getReceiver().getId())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }

    public MessageDTO createMessage(MessageCreationRequest messageCreationRequest, UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUserId();
        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Sender not found with id: " + userId));

        User receiver = userRepository.findById(messageCreationRequest.receiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found with id: " + messageCreationRequest.receiverId()));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(message.getContent());
        // sentAt is set by default in the entity

        Message savedMessage = messageRepository.save(message);
        return convertToDto(savedMessage);
    }

    public List<MessageDTO> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MessageDTO getMessageById(Long id) {
        return messageRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public List<MessageDTO> getMessagesBySenderId(Long senderId) {
        return messageRepository.findAllBySenderId(senderId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getMessagesByReceiverId(Long receiverId) {
        return messageRepository.findAllByReceiverId(receiverId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}