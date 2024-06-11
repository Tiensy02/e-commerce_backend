package SShop.mono.domain.entity.mongodb;

import SShop.mono.domain.entity.postgresql.BaseEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom extends BaseEntity {
  @Id
  private String id;

  private String chatId;

  private String senderId;

  private String recipientId;
}
