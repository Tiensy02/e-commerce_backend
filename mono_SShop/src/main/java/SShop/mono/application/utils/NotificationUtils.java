package SShop.mono.application.utils;

import SShop.mono.application.dto.notification.RawNotificationDto;
import SShop.mono.application.dto.notification.TargetNotification;
import SShop.mono.controler.client.NotificationClient;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NotificationUtils {

  private final NotificationClient notificationClient;

  public NotificationUtils(NotificationClient notificationClient) {
    this.notificationClient = notificationClient;
  }

  public TargetNotification createNotification(String channelId, Map<String, String> argValue,
      Map<String, String> targets) {
    RawNotificationDto rawNotificationDto = notificationClient.getNotificationContent(channelId);
    String content = QueryUtils.completeMess(argValue, rawNotificationDto.getContent());

    TargetNotification targetNotification = TargetNotification.builder()
        .content(content)
        .title(rawNotificationDto.getTitle())
        .targetIds(targets)
        .notificationType("product")
        .settingName("email")
        .build();
    return targetNotification;
  }
}
