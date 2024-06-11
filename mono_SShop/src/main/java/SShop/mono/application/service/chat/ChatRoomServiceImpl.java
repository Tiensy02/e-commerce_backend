package SShop.mono.application.service.chat;

import SShop.mono.domain.entity.mongodb.ChatRoom;
import SShop.mono.domain.repository.mogorepo.ChatRoomRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
  private ChatRoomRepository chatRoomRepository;
  @Override
  public String createChatId(String senderId, String recipientId) {
    var chatId = String.format("%s_%s", senderId, recipientId);

    ChatRoom senderRecipient = ChatRoom
        .builder()
        .chatId(chatId)
        .senderId(senderId)
        .recipientId(recipientId)
        .build();

    ChatRoom recipientSender = ChatRoom
        .builder()
        .chatId(chatId)
        .senderId(recipientId)
        .recipientId(senderId)
        .build();

    chatRoomRepository.save(senderRecipient);
    chatRoomRepository.save(recipientSender);

    return chatId;
  }

  @Override
  public Optional<String> getChatRoomId(String senderId, String recipientId) {
    return chatRoomRepository
        .findBySenderIdAndRecipientId(senderId, recipientId)
        .map(ChatRoom::getChatId)
        .or(() -> {
            var chatId = createChatId(senderId, recipientId);
            return Optional.of(chatId);
        });
  }
}
