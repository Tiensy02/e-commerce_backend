package SShop.mono.controler.product;

import SShop.mono.application.dto.product.CategoryDto;
import SShop.mono.controler.GenericCrudApi;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin
public interface CategoryApi extends GenericCrudApi<CategoryDto,String> {

}
