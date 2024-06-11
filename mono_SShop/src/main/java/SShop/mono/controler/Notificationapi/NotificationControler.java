package SShop.mono.controler.Notificationapi;

import SShop.mono.application.dto.email.EmailStructure;
import SShop.mono.application.service.email.EmailService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
public class NotificationControler implements NotificationApi{
private final EmailService emailService;
  @Override
  public ResponseEntity<?> getAuthenFromIDP(String code, String state) {
    System.out.println("code: " + code + " state: "  + state);
    return null;
  }

  @Override
  public ResponseEntity<?> sendEmail(EmailStructure emailStructure) {
    String id = UUID.randomUUID().toString();
    emailService.sendMessage(id, emailStructure);
    return ResponseEntity.ok("success");
  }


}
