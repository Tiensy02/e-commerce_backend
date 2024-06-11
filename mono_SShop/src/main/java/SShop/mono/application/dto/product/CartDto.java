package SShop.mono.application.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class CartDto {
  @NotNull
  private String userId;
}
