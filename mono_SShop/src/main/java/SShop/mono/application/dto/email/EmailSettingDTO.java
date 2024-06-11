package SShop.mono.application.dto.email;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
public class EmailSettingDTO {

  @Value("${spring.mail.host}")
  private String host;
  @Value("${spring.mail.port}")
  private String port;
  @Value("${spring.mail.username}")

  private String usename;
  @Value("${spring.mail.password}")
  private String password;
  @Value("${spring.mail.properties.mail.smtp.ssl.enable}")
  private Boolean enableSSL;
  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private Boolean enableStartTLS;
  public EmailSettingDTO() {

  }

  public EmailSettingDTO(String host, String port, String usename, String password,
      Boolean enableSSL,
      Boolean enableStartTLS) {
    this.host = host;
    this.port = port;
    this.usename = usename;
    this.password = password;
    this.enableSSL = enableSSL;
    this.enableStartTLS = enableStartTLS;
  }
}
