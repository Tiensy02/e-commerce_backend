package SShop.mono.application.service.chat;

import SShop.mono.application.dto.chat.ChatMessageDto;
import SShop.mono.application.dto.chat.ChatMessageSendDto;
import SShop.mono.domain.entity.mongodb.ChatMessage;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ChatMessageService {
  ChatMessage save(ChatMessageSendDto chatMessageSendDto);

  ChatMessageDto findChatMessage(String senderId, String recipientId );

  void processChat(ChatMessageSendDto chatMessageSendDto);
}
