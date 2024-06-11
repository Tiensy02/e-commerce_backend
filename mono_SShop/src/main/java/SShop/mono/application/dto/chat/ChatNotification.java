package SShop.mono.application.dto.chat;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatNotification {
  private String id;

  private String senderId;

  private String recipientId;

  private String content;

  private Date date;

}
