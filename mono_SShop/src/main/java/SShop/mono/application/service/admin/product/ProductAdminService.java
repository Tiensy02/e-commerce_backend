package SShop.mono.application.service.admin.product;

import SShop.mono.application.dto.product.CategoryDto;

public interface ProductAdminService {
  void createCategory(CategoryDto categoryDto);
  void addSubCategory(CategoryDto categoryDto);

}
