package SShop.mono.application.dto.product;

import SShop.mono.domain.model.CategoryShop;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class ShopDto {

  @NotNull
  private String userId;

  @NotNull
  private String description;

  private String imageBackground;

  private List<CategoryShop> category;
}
