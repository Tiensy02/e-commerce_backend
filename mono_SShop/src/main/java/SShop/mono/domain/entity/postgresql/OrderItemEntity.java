package SShop.mono.domain.entity.postgresql;

import SShop.mono.domain.constant.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "order_items")
@Data
public class OrderItemEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  private String id;

  private String productId;

  private int quantity;

  @ManyToOne()
  @JoinColumn(name = "order_id")
  @JsonManagedReference
  private OrderEntity order;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Type(JsonBinaryType.class)
  @Column(columnDefinition = "jsonb")
  Map<String,Object> customField;

  private String ownProductId;

}
