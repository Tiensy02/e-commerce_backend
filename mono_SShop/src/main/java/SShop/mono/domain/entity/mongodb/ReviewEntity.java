package SShop.mono.domain.entity.mongodb;

import SShop.mono.domain.entity.postgresql.BaseEntity;
import jakarta.persistence.Id;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "review")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity extends BaseEntity {
  @Id
  private String id;

  private String userId;

  private String productId;

  private String content;

  private String userAvatar;

  private String username;
  @Min(0)
  @Max(5)
  private int rating;
}

