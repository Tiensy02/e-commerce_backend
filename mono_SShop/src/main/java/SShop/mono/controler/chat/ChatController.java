package SShop.mono.controler.chat;

import SShop.mono.application.dto.chat.ChatMessageSendDto;
import SShop.mono.application.dto.chat.ChatNotification;
import SShop.mono.application.service.chat.ChatMessageService;
import SShop.mono.domain.entity.mongodb.ChatMessage;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController implements ChatApi {

  private final ChatMessageService chatMessageService;

  @Override
  public ResponseEntity<?> processMessage(ChatMessageSendDto chatMessageSendDto) {
    try {
      chatMessageService.processChat(chatMessageSendDto);
      return ResponseEntity.ok("success");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Override
  public ResponseEntity<?> getChatMessage(String senderId, String recipientId) {
    var result = chatMessageService.findChatMessage(senderId, recipientId);
    return ResponseEntity.ok(result);
  }
}
