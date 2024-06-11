package SShop.mono.controler.user;

import SShop.mono.application.dto.chat.UserConnectDto;
import SShop.mono.application.dto.product.UserUpdateNotificationSettingDto;
import SShop.mono.application.dto.user.UserUpdateDto;
import SShop.mono.domain.entity.mongodb.ReviewEntity;
import SShop.mono.domain.entity.mongodb.UserEntity;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public interface UserApi {
  @PostMapping("/review")
   ResponseEntity<?> postReview(@RequestBody ReviewEntity reviewEntity);

  @GetMapping("user-connected/{userId}")
  ResponseEntity<?> findConnectedUsers(@PathVariable String userId);

  /**
   * Param userConnecting: user which want to connect, userIdConnected: user which userConnecting
   * want to connect
   */
  @PostMapping("add/userConnected")
  ResponseEntity<?> addUserConnect(@RequestBody UserConnectDto userConnecting);

  @GetMapping("/{id}")
  ResponseEntity<?> get(@PathVariable String id);

  @PostMapping("/notificationSetting/update/{id}")
  ResponseEntity<?> updateNotificationSetting(@PathVariable String id, @RequestBody UserUpdateNotificationSettingDto dto);

  @PostMapping("/ids")
  ResponseEntity<?> getUserByIds(@RequestBody List<String> ids);

  @PostMapping("/update/{id}")
  ResponseEntity<?> update(@PathVariable String id, @RequestBody UserUpdateDto userUpdateDto);
}
