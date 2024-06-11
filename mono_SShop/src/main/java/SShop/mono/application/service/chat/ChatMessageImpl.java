package SShop.mono.application.service.chat;

import SShop.mono.application.dto.chat.ChatMessageDto;
import SShop.mono.application.dto.chat.ChatMessageSendDto;
import SShop.mono.application.dto.chat.ChatNotification;
import SShop.mono.domain.entity.mongodb.ChatMessage;
import SShop.mono.domain.repository.mogorepo.ChatMessageRepository;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatMessageImpl implements ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomService chatRoomService;
  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public ChatMessage save(ChatMessageSendDto chatMessageSendDto) {
    ChatMessage chatMessage = ChatMessage.builder()
        .content(chatMessageSendDto.getContent())
        .chatId(chatMessageSendDto.getChatId())
        .recipientId(chatMessageSendDto.getRecipientId())
        .senderId(chatMessageSendDto.getSenderId())
        .build();
    chatMessageRepository.save(chatMessage);

    return chatMessage;
  }

  @Override
  public ChatMessageDto findChatMessage(String senderId, String recipientId) {
    var chatId = chatRoomService.getChatRoomId(senderId, recipientId).orElse(null);
    List<ChatMessage> messages = chatMessageRepository.findByChatId(chatId);

    var result = ChatMessageDto.builder().content(messages).chatId(chatId).build();
    return result;
  }

  @Override
  public void processChat(ChatMessageSendDto chatMessageSendDto) {
    try {
      ChatMessage chatMessage = this.save(chatMessageSendDto);
      messagingTemplate.convertAndSendToUser(chatMessageSendDto.getRecipientId(), "/queue/messages",
          ChatNotification.builder()
              .recipientId(chatMessage.getRecipientId())
              .senderId(chatMessage.getSenderId())
              .id(chatMessage.getId())
              .content(chatMessage.getContent())
              .date(new Date())
              .build());
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
