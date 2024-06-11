package SShop.mono.domain.entity.postgresql;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "cart_items")

public class CartItemEntity extends BaseEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  private String id;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  @JsonBackReference
  private CartEntity cart;

  private String productId;

  @Type(JsonBinaryType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String,Object> customField;

}
