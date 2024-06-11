package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.OrderDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.domain.entity.postgresql.OrderEntity;

public interface OrderService extends GenericCrudService<OrderEntity,String, OrderDto> {

  OrderEntity getByUserId(String id);
}
