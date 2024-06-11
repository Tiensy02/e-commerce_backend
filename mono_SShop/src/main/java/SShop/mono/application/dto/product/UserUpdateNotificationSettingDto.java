package SShop.mono.application.dto.product;

import java.util.Map;
import lombok.Data;

@Data
public class UserUpdateNotificationSettingDto {
  private Map<String,String> notificationSetting;
}
