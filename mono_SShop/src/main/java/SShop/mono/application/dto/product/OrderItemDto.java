package SShop.mono.application.dto.product;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderItemDto {
  @NotNull
  private String orderId;
  @NotNull
  private String productId;
  @NotNull
  private double price;
  @NotNull
  private int quantity;
  @NotNull
  private String ownProductId;

  private Map<String,Object> customField;
}
