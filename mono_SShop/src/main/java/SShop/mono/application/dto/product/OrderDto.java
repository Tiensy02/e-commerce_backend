package SShop.mono.application.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderDto {
  @NotNull
  private String userId;
}
