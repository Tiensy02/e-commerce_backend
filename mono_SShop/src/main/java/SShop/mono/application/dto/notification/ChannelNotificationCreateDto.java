package SShop.mono.application.dto.notification;


import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelNotificationCreateDto {

  @NotNull
  private String settingName;

  @NotNull
  private String eventNotificationId;

  @NotNull
  private String notificationTemplateId;

  private List<String> userIds;

}
