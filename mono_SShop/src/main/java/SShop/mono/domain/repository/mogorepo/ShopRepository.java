package SShop.mono.domain.repository.mogorepo;

import SShop.mono.domain.entity.mongodb.ShopEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends MongoRepository<ShopEntity,String> {
  ShopEntity findByUserId(String userId);
}
