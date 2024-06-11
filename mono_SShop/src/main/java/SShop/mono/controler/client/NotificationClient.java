package SShop.mono.controler.client;

import SShop.mono.application.dto.notification.ChannelNotificationCreateDto;
import SShop.mono.application.dto.notification.RawNotificationDto;
import SShop.mono.application.dto.notification.TargetNotification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "${application.config.notification-url}")
public interface NotificationClient {
  @PostMapping("/channel/create")
  String createChanel(ChannelNotificationCreateDto channelNotificationCreateDto);

  @GetMapping("/channel/content/{id}")
  RawNotificationDto getNotificationContent(@PathVariable String id);

  @PostMapping("/send")
  String sendNotification(@RequestBody TargetNotification targetNotification);
}
