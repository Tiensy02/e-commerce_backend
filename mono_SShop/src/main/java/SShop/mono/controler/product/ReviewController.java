package SShop.mono.controler.product;

import SShop.mono.application.dto.product.ReviewDto;
import SShop.mono.application.service.user.ReviewService;
import SShop.mono.controler.GenericCrudController;
import SShop.mono.domain.entity.mongodb.ReviewEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController extends GenericCrudController<ReviewEntity, ReviewDto,String> implements ReviewApi  {

  public ReviewController(ReviewService reviewService) {
    super(reviewService);
  }
}
