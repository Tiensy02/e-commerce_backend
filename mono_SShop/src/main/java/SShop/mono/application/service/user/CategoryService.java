package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.CategoryDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.domain.entity.postgresql.CategoryEntity;

public interface CategoryService extends
    GenericCrudService<CategoryEntity,String, CategoryDto> {

}
