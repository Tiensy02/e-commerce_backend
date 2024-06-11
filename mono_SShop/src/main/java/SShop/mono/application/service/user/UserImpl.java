package SShop.mono.application.service.user;

import SShop.mono.application.dto.chat.UserConnectDto;
import SShop.mono.application.dto.product.UserUpdateNotificationSettingDto;
import SShop.mono.application.dto.user.UserUpdateDto;
import SShop.mono.application.utils.ObjectUtils;
import SShop.mono.domain.entity.mongodb.ReviewEntity;
import SShop.mono.domain.entity.mongodb.UserEntity;
import SShop.mono.domain.model.UserConnect;
import SShop.mono.domain.repository.mogorepo.ReviewRepository;
import SShop.mono.domain.repository.mogorepo.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserImpl implements UserService {

  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  @Override
  public void postReview(ReviewEntity reviewEntity) {
    reviewRepository.save(reviewEntity);
  }


  @Override
  public Set<UserConnect> findConnectUser(String userId) {
    var userStored = userRepository.findById(userId)
        .orElse(null);
    if (userStored != null) {
      return userStored.getUsersConnected();
    }
    return null;
  }

  /**
   * Param userConnecting: user which want to connect userIdConnected: user which userConnecting
   * want to connect
   */

  @Override
  public UserEntity addUserConnected(UserConnectDto userConnect)
      throws NotFoundException {
    UserEntity user = userRepository.findById(userConnect.getUserConnectedId())
        .orElseThrow(() -> new NotFoundException());

    UserEntity userConnecting = userRepository.findById(userConnect.getUserConnectingId())
        .orElseThrow(() -> new NotFoundException());

    if (userConnecting.getUsersConnected() != null && userConnecting.getUsersConnected().contains(user)) {
      return user;
    } else {
      UserConnect userConnected = new UserConnect(user.getId(), user.getAvatar(),
          user.getUserNameAlias());

      UserConnect userConnectingModel = new UserConnect(userConnecting.getId(),
          userConnecting.getAvatar(), userConnecting.getUserNameAlias());

      userConnecting.addUserConnected(userConnected);
      user.addUserConnected(userConnectingModel);

      userRepository.save(user);
      userRepository.save(userConnecting);
      return user;
    }

  }

  @Override
  public UserEntity get(String id) throws NotFoundException {
    var result = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
    return result;
  }

  @Override
  public List<UserEntity> getUserByIds(List<String> ids) {
    return userRepository.findAllById(ids);
  }

  @Override
  public Map<String, String> updateNotificationSetting(String id,
      UserUpdateNotificationSettingDto dto) throws NotFoundException {
    UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
    user.setNotificationSetting(dto.getNotificationSetting());
    userRepository.save(user);
    return dto.getNotificationSetting();
  }

  @Override
  public UserEntity update(String id, UserUpdateDto userUpdateDto) throws NotFoundException {
    UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
    ObjectUtils.copyNonNullProperties(userUpdateDto, user);
    return userRepository.save(user);
  }

}
