package SShop.mono.controler.user;

import SShop.mono.application.dto.chat.UserConnectDto;
import SShop.mono.application.dto.product.UserUpdateNotificationSettingDto;
import SShop.mono.application.dto.user.UserUpdateDto;
import SShop.mono.application.service.user.UserService;
import SShop.mono.domain.entity.mongodb.ReviewEntity;
import SShop.mono.domain.entity.mongodb.UserEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {

  private final UserService userService;

  @Override
  public ResponseEntity<?> postReview(ReviewEntity reviewEntity) {
    try {
      userService.postReview(reviewEntity);
      return ResponseEntity.ok("create review success");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("something wrong");
    }
  }

  @Override
  public ResponseEntity<?> findConnectedUsers(String userId) {
    var result = userService.findConnectUser(userId);
    return ResponseEntity.ok(result);
  }

  @Override
  public ResponseEntity<?> addUserConnect(UserConnectDto userConnectDto) {
    try {
      var result =  userService.addUserConnected(userConnectDto);
      return ResponseEntity.ok(result);
    } catch (NotFoundException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @Override
  public ResponseEntity<?> get(String id) {
    try {
      var result = userService.get(id);
      return ResponseEntity.ok(result);
    } catch (NotFoundException e) {
      return ResponseEntity.status(404).body("user khong ton tai");
    }
  }

  @Override
  public ResponseEntity<?> updateNotificationSetting(String id,
      UserUpdateNotificationSettingDto dto) {
    try {
          var result = userService.updateNotificationSetting(id,dto);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> getUserByIds(List<String> ids) {
    try {
          var result = userService.getUserByIds(ids);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> update(String id, UserUpdateDto userUpdateDto) {
    try {
          var result = userService.update(id,userUpdateDto);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }
}

