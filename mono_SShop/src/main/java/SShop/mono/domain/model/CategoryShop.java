package SShop.mono.domain.model;

import java.util.List;
import lombok.Data;

@Data
public class CategoryShop {
  private String name;

  private List<CategoryShop> categoryShopChild;

  private List<String> productIds;
}
