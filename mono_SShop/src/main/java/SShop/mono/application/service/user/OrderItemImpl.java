package SShop.mono.application.service.user;

import SShop.mono.application.dto.notification.RawNotificationDto;
import SShop.mono.application.dto.notification.TargetNotification;
import SShop.mono.application.dto.product.OrderItemDto;
import SShop.mono.application.dto.product.OrderItemUpdateDto;
import SShop.mono.application.service.GenericCrudImplJpa;
import SShop.mono.application.utils.NotificationUtils;
import SShop.mono.application.utils.ObjectUtils;
import SShop.mono.application.utils.QueryUtils;
import SShop.mono.controler.client.NotificationClient;
import SShop.mono.domain.constant.OrderStatus;
import SShop.mono.domain.entity.mongodb.ProductEntity;
import SShop.mono.domain.entity.mongodb.UserEntity;
import SShop.mono.domain.entity.postgresql.CartEntity;
import SShop.mono.domain.entity.postgresql.CartItemEntity;
import SShop.mono.domain.entity.postgresql.OrderEntity;
import SShop.mono.domain.entity.postgresql.OrderItemEntity;
import SShop.mono.domain.repository.mogorepo.ProductRepository;
import SShop.mono.domain.repository.mogorepo.UserRepository;
import SShop.mono.domain.repository.postgrerepo.CartItemRepository;
import SShop.mono.domain.repository.postgrerepo.CartRepository;
import SShop.mono.domain.repository.postgrerepo.OrderItemRepository;
import SShop.mono.domain.repository.postgrerepo.OrderRepository;
import jakarta.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderItemImpl extends
    GenericCrudImplJpa<OrderItemEntity, String, OrderItemDto> implements OrderItemService {

  private final OrderItemRepository orderItemRepository;

  private final OrderRepository orderRepository;

  private final UserRepository userRepository;

  private final ProductRepository productRepository;

  private final NotificationClient notificationClient;

  private final NotificationUtils notificationUtils;

  private final CartRepository cartRepository;

  private final CartItemRepository cartItemRepository;

  private final CartItemService cartItemService;
  public OrderItemImpl(OrderItemRepository orderItemRepository, OrderRepository orderRepository,
      UserRepository userRepository, ProductRepository productRepository,
      NotificationClient notificationClient, NotificationUtils notificationUtils,
      CartRepository cartRepository, CartItemRepository cartItemRepository,
      CartItemService cartItemService) {
    super(orderItemRepository);
    this.orderItemRepository = orderItemRepository;
    this.orderRepository = orderRepository;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
    this.notificationClient = notificationClient;
    this.notificationUtils = notificationUtils;
    this.cartRepository = cartRepository;
    this.cartItemRepository = cartItemRepository;
    this.cartItemService = cartItemService;
  }

  @Override
  public OrderItemEntity convertDtoToEntity(OrderItemDto dto) {
    OrderItemEntity orderItem = new OrderItemEntity();
    ObjectUtils.copyNonNullProperties(dto, orderItem);
    return orderItem;
  }

  @Override
  public OrderItemDto convertEntityToDto(OrderItemEntity entity) {
    OrderItemDto orderItemDto = new OrderItemDto();
    ObjectUtils.copyNonNullProperties(entity, orderItemDto);
    return orderItemDto;
  }

  @Override
  public OrderItemEntity save(OrderItemDto orderItemDto) {

    OrderEntity order = orderRepository.findById(orderItemDto.getOrderId())
        .orElseThrow(() -> new NotFoundException());

    UserEntity user = userRepository.findById(order.getUserId())
        .orElseThrow(() -> new NotFoundException());

    ProductEntity product = productRepository.findById(orderItemDto.getProductId())
        .orElseThrow(() -> new NotFoundException());

    UserEntity userOwnProduct = userRepository.findById(product.getUserId())
        .orElseThrow(() -> new NotFoundException());

    OrderItemEntity orderItem = new OrderItemEntity();
    orderItem.setOrder(order);
    ObjectUtils.copyNonNullProperties(orderItemDto,orderItem);
    orderItem.setStatus(OrderStatus.CONFIRM);

    orderItem = orderItemRepository.save(orderItem);
    product.setSold(product.getSold()+1);
    product.setStock(product.getStock()-1);
    productRepository.save(product);
    deleteCartItem(user.getId(),product.getId());

    try {

      String channelId = product.getChanelIds().get("shop_sold");
      Map<String,String> argsValue = new HashMap<>();
      argsValue.put("product_name",product.getName());
      argsValue.put("usernames",user.getUserNameAlias());
      Map<String,String> targets = new HashMap<>();
      targets.put(userOwnProduct.getId(),userOwnProduct.getNotificationSetting().get("email"));

      TargetNotification targetNotification = notificationUtils.createNotification(channelId,argsValue,targets);

      notificationClient.sendNotification(targetNotification);
    } catch (Exception e) {
      log.error("send notification false to: " + userOwnProduct.getUserNameAlias() + " about " + product.getName());
    }

    return orderItem;
  }
  private void deleteCartItem(String userId, String productId) {
    CartEntity cart = cartRepository.findByUserId(userId);
    CartItemEntity cartItem = cartItemRepository.findByCartAndProductId(cart,productId);
    cartItemService.removeCartItem(cartItem);
  }

  @Override
  public List<OrderItemEntity> getByOrderId(String id) {
    OrderEntity order = orderRepository.findById(id).orElseThrow(()-> new NotFoundException());
    return orderItemRepository.findAllByOrder(order);
  }

  @Override
  public List<OrderItemEntity> getByOwnShopId(String userId) {
    return orderItemRepository.findByOwnProductId(userId);
  }

  @Override
  public List<OrderItemEntity> update(OrderItemUpdateDto updateDto) {
    List<OrderItemEntity> orderItems = orderItemRepository.findAllById(updateDto.getOrderItemId());
    for(OrderItemEntity orderItem : orderItems ) {
      orderItem.setStatus(updateDto.getStatus());
    }
    return orderItemRepository.saveAll(orderItems);
  }
}
