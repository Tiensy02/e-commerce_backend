package SShop.mono.application.service.user;

import SShop.mono.application.dto.notification.ChannelNotificationCreateDto;
import SShop.mono.application.dto.notification.RawNotificationDto;
import SShop.mono.application.dto.notification.TargetNotification;
import SShop.mono.application.dto.product.ProductDto;
import SShop.mono.application.service.GenericCrudImplSpringMongoDb;
import SShop.mono.application.utils.ObjectUtils;
import SShop.mono.application.utils.QueryUtils;
import SShop.mono.controler.client.NotificationClient;
import SShop.mono.domain.entity.mongodb.ProductEntity;
import SShop.mono.domain.entity.mongodb.ReviewEntity;
import SShop.mono.domain.entity.mongodb.UserEntity;
import SShop.mono.domain.repository.mogorepo.ProductRepository;
import SShop.mono.domain.repository.mogorepo.ReviewRepository;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductImpl extends GenericCrudImplSpringMongoDb<ProductEntity,String,ProductDto> implements ProductService {

  private final ReviewRepository reviewRepository;

  private final NotificationClient notificationClient;

  private final ProductRepository productRepository;

  private final UserService userService;

  @Value("${application.notification.discount-product-template}")
  private String discountTemplate;

  @Value("${application.notification.discount-product-event}")
  private String discountEvent;

  @Value("${application.notification.purchase-notification-template}")
  private String purchaseTemplate;

  @Value("${application.notification.purchase-notification-event}")
  private String purchaseEvent;


  protected ProductImpl(ProductRepository productRepository, ReviewRepository reviewRepository,
      NotificationClient notificationClient, UserService userService) {
    super(productRepository);
    this.reviewRepository = reviewRepository;
    this.notificationClient = notificationClient;
    this.productRepository = productRepository;
    this.userService = userService;
  }

  @Override
  public Page<ReviewEntity> getReviews(String productId, Pageable page) {
    return reviewRepository.findByProductId(productId,page);
  }

  @Override
  public List<ProductEntity> getByIds(List<String> ids) {
    var result = productRepository.findAllById(ids);
    return result;
  }

  @Override
  public ProductEntity convertDtoToEntity(ProductDto dto) {
    ProductEntity productEntity = new ProductEntity();
    ObjectUtils.copyNonNullProperties(dto,productEntity);
    return productEntity;
  }

  @Override
  public ProductDto convertEntityToDto(ProductEntity entity) {
    ProductDto productDto = new ProductDto();
    ObjectUtils.copyNonNullProperties(entity,productDto);
    return productDto;
  }

  @Override
  public ProductEntity save(ProductDto productDto) {

    ProductEntity entity = convertDtoToEntity(productDto);
    String channelPushId =  createChannelPushNotification();
    String channelEmailId = createChannelEmail(productDto);

    Map<String,String> channelIds = new HashMap<>();
    channelIds.put("user_discount",channelPushId);
    channelIds.put("shop_sold",channelEmailId);
    entity.setChanelIds(channelIds);

    return productRepository.save(entity);
  }

  @Override
  public ProductEntity update(String id, ProductDto productDto) {

    ProductEntity product = productRepository.findById(id).orElseThrow(()-> new NotFoundException());

    if(productDto.getDiscount() != product.getDiscount() ) {
      String channelId = product.getChanelIds().get("user_discount");

      if( channelId != null ) {
        RawNotificationDto rawNotificationDto = notificationClient.getNotificationContent(channelId);

        Map<String,String> argsValue = new HashMap<>();
        argsValue.put("product_name",product.getName());
        String content = QueryUtils.completeMess(argsValue,rawNotificationDto.getContent());

        List<UserEntity> users = userService.getUserByIds(rawNotificationDto.getUserIds());
        Map<String,String> targets = new HashMap<>();

        for(UserEntity user : users) {
          targets.put(user.getId(),user.getNotificationSetting().get("push-notification"));
        }

        Map<String,Object> customField = new HashMap<>();
        customField.put("link","http://localhost:5173/product/" + id);
        customField.put("image",product.getImagePrimary());

        TargetNotification targetNotification = TargetNotification.builder()
            .content(content)
            .title(rawNotificationDto.getTitle())
            .targetIds(targets)
            .notificationType("product")
            .settingName("push-notification")
            .customField(customField)
            .build();

        notificationClient.sendNotification(targetNotification);
      }

    }
    ObjectUtils.copyNonNullProperties(productDto,product);
    productRepository.save(product);

    return product;
  }

  public String createChannelEmail(ProductDto productDto) {
    ChannelNotificationCreateDto channelEmail = ChannelNotificationCreateDto.builder()
        .notificationTemplateId(purchaseTemplate)
        .eventNotificationId(purchaseEvent)
        .settingName("email")
        .userIds(Arrays.asList(productDto.getUserId()))
        .build();

    return  notificationClient.createChanel(channelEmail);
  }

  public String createChannelPushNotification() {
    ChannelNotificationCreateDto channelPush =  ChannelNotificationCreateDto.builder()
        .settingName("push-notification")
        .notificationTemplateId(discountTemplate)
        .eventNotificationId(discountEvent)
        .build();
    return notificationClient.createChanel(channelPush);
  }

}
