package SShop.mono.domain.repository.mogorepo;

import SShop.mono.domain.entity.mongodb.ChatRoom;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {
  Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
