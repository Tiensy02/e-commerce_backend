package SShop.mono.controler.product;

import SShop.mono.application.dto.product.CategoryDto;
import SShop.mono.application.service.user.CategoryService;
import SShop.mono.controler.GenericCrudController;
import SShop.mono.domain.entity.postgresql.CategoryEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController extends GenericCrudController<CategoryEntity, CategoryDto,String> implements CategoryApi {

  public CategoryController(CategoryService categoryService) {
    super(categoryService);
  }
}
