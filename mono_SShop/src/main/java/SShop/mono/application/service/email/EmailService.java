package SShop.mono.application.service.email;

import SShop.mono.application.dto.email.EmailStructure;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

  void sendMessage(String id, EmailStructure emailStructure);

  void sendMessage(MimeMessage message);

  void updateConfig();

}
