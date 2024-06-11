package SShop.mono.domain.repository.mogorepo;

import SShop.mono.domain.entity.mongodb.ProductEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
  List<ProductEntity> findByUserId(String userId);
  List<ProductEntity> findAllById(Iterable<String> ids);
}
