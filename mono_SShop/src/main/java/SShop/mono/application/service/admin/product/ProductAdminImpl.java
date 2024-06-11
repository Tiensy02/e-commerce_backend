package SShop.mono.application.service.admin.product;

import SShop.mono.application.dto.product.CategoryDto;
import SShop.mono.domain.entity.postgresql.CategoryEntity;
import SShop.mono.domain.repository.postgrerepo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductAdminImpl implements ProductAdminService {
  private final CategoryRepository categoryRepository;

  @Override
  public void createCategory(CategoryDto categoryDto) {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setCategoryName(categoryDto.getCategoryName());
    categoryEntity.setCategoryDescription(categoryDto.getCategoryDescription());
    categoryRepository.save(categoryEntity);
  }

  @Override
  public void addSubCategory(CategoryDto categoryDto) {
    CategoryEntity categoryEntity = categoryRepository.findById(categoryDto.getParentCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
    CategoryEntity subCategory = new CategoryEntity();
    subCategory.setCategoryName(categoryDto.getCategoryName());
    subCategory.setCategoryDescription(categoryDto.getCategoryDescription());
    subCategory.setParentCategory(categoryEntity);
    categoryRepository.save(subCategory);
  }


}
