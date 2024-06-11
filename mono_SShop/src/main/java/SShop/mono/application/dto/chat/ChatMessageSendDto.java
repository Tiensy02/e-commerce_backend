package SShop.mono.application.dto.chat;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatMessageSendDto {
  @NotNull
  private String chatId;

  @NotNull
  private String senderId;

  @NotNull
  private String recipientId;

  private String content;
}
