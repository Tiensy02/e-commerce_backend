package SShop.mono.application.dto.chat;
import SShop.mono.domain.entity.mongodb.ChatMessage;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageDto {
  private String chatId;

  private List<ChatMessage> content;
}
