package SShop.mono.application.dto.notification;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RawNotificationDto {

  private String content;

  private String title;

  private List<String> args;

  private List<String> userIds;

  private String settingName;
}
