package SShop.mono.domain.repository.mogorepo;

import SShop.mono.domain.entity.mongodb.UserEntity;
import SShop.mono.domain.model.UserConnect;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableMongoRepositories
public interface UserRepository extends MongoRepository<UserEntity, String> {
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findByUserName(String userName);

  @Query(value = "{ '_id' : {'$in' : ?0 } }", fields = "{ 'description': 0 }")
  Iterable<UserEntity> findAllThin(Iterable<String> ids);
}