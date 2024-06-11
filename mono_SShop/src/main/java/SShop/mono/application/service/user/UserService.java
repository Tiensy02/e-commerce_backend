package SShop.mono.application.service.user;

import SShop.mono.application.dto.chat.UserConnectDto;
import SShop.mono.application.dto.product.UserUpdateNotificationSettingDto;
import SShop.mono.application.dto.user.UserUpdateDto;
import SShop.mono.domain.entity.mongodb.ReviewEntity;
import SShop.mono.domain.entity.mongodb.UserEntity;
import SShop.mono.domain.model.UserConnect;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface UserService {
  void postReview(ReviewEntity reviewEntity);

  Set<UserConnect> findConnectUser(String userId);

  UserEntity addUserConnected(UserConnectDto userConnecting) throws NotFoundException;


  UserEntity get(String id) throws NotFoundException;

  List<UserEntity> getUserByIds(List<String> ids);

  Map<String,String> updateNotificationSetting(String id, UserUpdateNotificationSettingDto dto)
      throws NotFoundException;

  UserEntity update(String id,UserUpdateDto userUpdateDto) throws NotFoundException;
}
