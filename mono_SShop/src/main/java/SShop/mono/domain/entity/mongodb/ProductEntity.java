package SShop.mono.domain.entity.mongodb;

import SShop.mono.domain.entity.postgresql.BaseEntity;
import SShop.mono.domain.entity.postgresql.CategoryEntity;
import com.google.gson.JsonArray;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "product")
public class ProductEntity extends BaseEntity {
  @Id
  private String id;

  private String userId;

  private String categoryId;

  private String name;

  private List<String> images;

  private String imagePrimary;

  private String description;

  private List<String> tags;

  private String subTitle;

  private float price;

  private float discount;

  private float ratting;

  private int totalReview;

  // kind of product and quantity
  // VD:
      // { "size": S, "stock": 5 }
      // { "{"color":"red","size":"S"}, "stock": 10 }
  private Map<String,Object> customField;

  private Map<String,String> chanelIds;

  private int sold;

  private int stock;
}
