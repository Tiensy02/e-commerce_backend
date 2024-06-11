package SShop.mono.application.service.email;

import SShop.mono.application.dto.email.EmailStructure;
import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSMTPImpl implements  EmailService{

  private final JavaMailSender emailSender;
  @Value("${application.email.tracking.url}")
  private  String EMAIL_TRACKING_URL;

  public EmailSMTPImpl(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  public void sendMessage(String id, EmailStructure emailStructure) {
    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
      helper.setFrom(emailStructure.getFrom());
      helper.setTo(emailStructure.getTo());
      helper.setSubject(emailStructure.getSubject());
      helper.setText(addTracking(id, emailStructure.getBody()), true);
      sendMessage(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sendMessage(MimeMessage message) {

    emailSender.send(message);
  }


  @Override
  @PostConstruct
  public void updateConfig() {

  }
  private String addTracking(String id, String content) {
    return content + "<img src='" + EMAIL_TRACKING_URL + "/" + id + ".png'>";
//    return content;
  }


}
