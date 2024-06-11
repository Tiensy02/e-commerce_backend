package SShop.mono.controler.chat;

import SShop.mono.application.dto.chat.ChatMessageSendDto;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
@CrossOrigin
public interface ChatApi {

  @MessageMapping("/chat")
  ResponseEntity<?> processMessage(@Payload ChatMessageSendDto chatMessageSendDto);

  @GetMapping("/message/{senderId}/{recipientId}")
  ResponseEntity<?> getChatMessage(@PathVariable String senderId, @PathVariable String recipientId);
}
