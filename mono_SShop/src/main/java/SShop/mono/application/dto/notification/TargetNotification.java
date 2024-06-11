package SShop.mono.application.dto.notification;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TargetNotification {

  private Map<String,String> targetIds;

  private String settingName;

  private String notificationType;

  private String content;

  private String title;

  private Map<String,Object> customField;

}
