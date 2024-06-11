package SShop.mono.application.service.otp;

import dev.samstevens.totp.exceptions.CodeGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Test {
  @Autowired
  private OtpService otpService;
//  @Bean
//  public int testOtp() throws CodeGenerationException {
//    String code = otpService.generateOTP("12345678");
//    return 1;
//  }
}
