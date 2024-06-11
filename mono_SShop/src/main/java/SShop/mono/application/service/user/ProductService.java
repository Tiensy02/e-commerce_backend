package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.ProductDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.domain.entity.mongodb.ProductEntity;
import SShop.mono.domain.entity.mongodb.ReviewEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public interface ProductService extends GenericCrudService<ProductEntity,String,ProductDto> {
  Page<ReviewEntity> getReviews(String productId,Pageable page);

  List<ProductEntity> getByIds(List<String> ids);
}
