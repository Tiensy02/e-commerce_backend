package SShop.mono.domain.repository.mogorepo;

import SShop.mono.domain.entity.mongodb.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<ReviewEntity,String> {
  Page<ReviewEntity> findByProductId(String productId, Pageable page);
}
