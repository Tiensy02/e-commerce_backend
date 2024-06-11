package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.ProductShowDto;
import SShop.mono.application.dto.product.ShopDto;
import SShop.mono.application.service.GenericCrudImplSpringMongoDb;
import SShop.mono.application.utils.ObjectUtils;
import SShop.mono.domain.entity.mongodb.ProductEntity;
import SShop.mono.domain.entity.mongodb.ShopEntity;
import SShop.mono.domain.repository.mogorepo.ProductRepository;
import SShop.mono.domain.repository.mogorepo.ShopRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopImpl extends GenericCrudImplSpringMongoDb<ShopEntity,String, ShopDto> implements ShopService {
  private final ShopRepository shopRepository;
  private final ProductRepository productRepository;

  protected ShopImpl(ShopRepository shopRepository, ProductRepository productRepository){
    super(shopRepository);
    this.shopRepository = shopRepository;
    this.productRepository = productRepository;
  }

  @Override
  public ShopEntity convertDtoToEntity(ShopDto dto) {
    ShopEntity shopEntity = new ShopEntity();
    ObjectUtils.copyNonNullProperties(dto,shopEntity);
    return shopEntity;
  }

  @Override
  public ShopDto convertEntityToDto(ShopEntity entity) {
    ShopDto shopDto = new ShopDto();
    ObjectUtils.copyNonNullProperties(entity,shopDto);
    return shopDto;
  }

  @Override
  public List<ProductEntity> getProducts(String ownId) {
    return productRepository.findByUserId(ownId);
  }

  @Override
  public ShopEntity getByUser(String id) {
    return shopRepository.findByUserId(id);
  }
}
