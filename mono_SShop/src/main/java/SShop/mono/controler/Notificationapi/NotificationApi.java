package SShop.mono.controler.Notificationapi;

import SShop.mono.application.dto.email.EmailStructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public interface NotificationApi {
  @GetMapping("/authen/auth-response")
  ResponseEntity<?> getAuthenFromIDP(@RequestParam("code") String code, @RequestParam("state") String state);

  @PostMapping("/notification/email")
  ResponseEntity<?> sendEmail(@RequestBody EmailStructure emailStructure);



}
