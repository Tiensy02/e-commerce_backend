package SShop.mono.application.dto.product;

import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Data;

@Data
public class CartItemDto {
  @NotNull
  private String cartId;
  @NotNull
  private String productId;
  @NotNull
  private Map<String,Object> customField;
}
