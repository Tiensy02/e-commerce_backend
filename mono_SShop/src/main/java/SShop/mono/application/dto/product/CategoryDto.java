package SShop.mono.application.dto.product;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class CategoryDto {
  @NotNull
  private String categoryName;
  @NotNull
  private String categoryDescription;

  private String parentCategoryId;

}
