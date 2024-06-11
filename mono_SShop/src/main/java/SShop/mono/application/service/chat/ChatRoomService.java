package SShop.mono.application.service.chat;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface ChatRoomService {
  String createChatId(String senderId, String recipientId);
  Optional<String> getChatRoomId(String senderId, String recipientId );
}
