package SShop.mono.domain.entity.mongodb;

import SShop.mono.domain.entity.postgresql.BaseEntity;
import SShop.mono.domain.model.CategoryShop;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "shop")
public class ShopEntity extends BaseEntity {
  @Id
  private String id;

  @Indexed(unique = true)
  private String userId;

  private String description;

  private String imageBackground;

  private List<CategoryShop> category;

  private float evaluate;

  private int seen;

  private String chanelId;
}
