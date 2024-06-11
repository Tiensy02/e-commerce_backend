package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.ReviewDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.domain.entity.mongodb.ReviewEntity;

public interface ReviewService extends GenericCrudService<ReviewEntity,String, ReviewDto> {

}
