package SShop.mono.domain.repository.mogorepo;

import SShop.mono.domain.entity.mongodb.ChatMessage;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
  List<ChatMessage> findByChatId(String chatId);
}
