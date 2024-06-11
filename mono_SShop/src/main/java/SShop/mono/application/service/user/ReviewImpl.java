package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.ReviewDto;
import SShop.mono.application.service.GenericCrudImplSpringMongoDb;
import SShop.mono.application.utils.ObjectUtils;
import SShop.mono.domain.entity.mongodb.ReviewEntity;
import SShop.mono.domain.repository.mogorepo.ReviewRepository;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewImpl extends GenericCrudImplSpringMongoDb<ReviewEntity,String,ReviewDto> implements ReviewService {

  protected ReviewImpl(ReviewRepository reviewRepository) {
    super(reviewRepository);
  }

  @Override
  public ReviewEntity convertDtoToEntity(ReviewDto dto) {
    ReviewEntity review = new ReviewEntity();
    ObjectUtils.copyNonNullProperties(dto,review);
    return review;
  }

  @Override
  public ReviewDto convertEntityToDto(ReviewEntity entity) {
    ReviewDto reviewDto = new ReviewDto();
    ObjectUtils.copyNonNullProperties(entity,reviewDto);
    return reviewDto;
  }
}
