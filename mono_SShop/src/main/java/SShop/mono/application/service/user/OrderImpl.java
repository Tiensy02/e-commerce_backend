package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.OrderDto;
import SShop.mono.application.service.GenericCrudImplJpa;
import SShop.mono.application.utils.ObjectUtils;
import SShop.mono.domain.entity.postgresql.OrderEntity;
import SShop.mono.domain.repository.postgrerepo.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderImpl extends GenericCrudImplJpa<OrderEntity,String, OrderDto> implements
    OrderService {

  private final OrderRepository orderRepository;
  public OrderImpl(OrderRepository orderRepository) {
    super(orderRepository);
    this.orderRepository = orderRepository;
  }

  @Override
  public OrderEntity convertDtoToEntity(OrderDto dto) {
    OrderEntity order = new OrderEntity();
    ObjectUtils.copyNonNullProperties(dto,order);
    return order;
  }

  @Override
  public OrderDto convertEntityToDto(OrderEntity entity) {
    OrderDto orderDto = new OrderDto();
    ObjectUtils.copyNonNullProperties(entity,orderDto);
    return orderDto;
  }

  @Override
  public OrderEntity getByUserId(String id) {
    OrderEntity order = orderRepository.findByUserId(id);
    if(order == null ) {
      OrderEntity newOrder = new OrderEntity();
      newOrder.setUserId(id);
      return orderRepository.save(newOrder);
    }
    else return order;
  }
}
