package SShop.mono.controler.product;

import SShop.mono.application.dto.product.ReviewDto;
import SShop.mono.controler.GenericCrudApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
public interface ReviewApi extends GenericCrudApi<ReviewDto,String> {

}
