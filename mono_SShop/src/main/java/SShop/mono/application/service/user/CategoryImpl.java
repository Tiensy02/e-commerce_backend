package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.CategoryDto;
import SShop.mono.application.service.GenericCrudImplJpa;
import SShop.mono.application.utils.ObjectUtils;
import SShop.mono.domain.entity.postgresql.CategoryEntity;
import SShop.mono.domain.repository.postgrerepo.CategoryRepository;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CategoryImpl extends GenericCrudImplJpa<CategoryEntity, String, CategoryDto> implements
    CategoryService {

  public CategoryImpl(CategoryRepository categoryRepository) {
    super(categoryRepository);
  }

  @Override
  public CategoryEntity convertDtoToEntity(CategoryDto dto) {
    CategoryEntity category = new CategoryEntity();
    ObjectUtils.copyNonNullProperties(dto, category);
    return category;
  }

  @Override
  public CategoryDto convertEntityToDto(CategoryEntity entity) {
    CategoryDto categoryDto = new CategoryDto();
    ObjectUtils.copyNonNullProperties(entity, categoryDto);
    return categoryDto;
  }

  @Override
  public CategoryEntity save(CategoryDto dto) throws NotFoundException {
    CategoryEntity category = null;
    if (dto.getParentCategoryId() != null) {
      category = repository.findById(dto.getParentCategoryId())
          .orElseThrow(() -> new NotFoundException());
    }
    CategoryEntity subCategory = new CategoryEntity();
    subCategory.setCategoryName(dto.getCategoryName());
    subCategory.setCategoryDescription(dto.getCategoryDescription());
    if (category != null) {
      subCategory.setParentCategory(category);
    }
    return repository.save(subCategory);
  }
}
