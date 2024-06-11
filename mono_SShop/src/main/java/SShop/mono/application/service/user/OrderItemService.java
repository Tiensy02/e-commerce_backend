package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.OrderItemDto;
import SShop.mono.application.dto.product.OrderItemUpdateDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.domain.entity.postgresql.OrderItemEntity;
import java.util.List;

public interface OrderItemService extends
    GenericCrudService<OrderItemEntity,String, OrderItemDto> {

  List<OrderItemEntity> getByOrderId(String id);

  List<OrderItemEntity> getByOwnShopId(String userId);

  List<OrderItemEntity> update(OrderItemUpdateDto updateDto);
}
