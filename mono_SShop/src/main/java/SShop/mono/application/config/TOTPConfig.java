package SShop.mono.application.config;

import static SShop.mono.application.service.otp.TOTPConstants.CODE_VALIDITY_IN_SECONDS;
import static SShop.mono.application.service.otp.TOTPConstants.DIGITS;
import static SShop.mono.application.service.otp.TOTPConstants.HASHING_ALGO;
import static SShop.mono.application.service.otp.TOTPConstants.SECRET_CHARACTER_LENGTH;
import static SShop.mono.application.service.otp.TOTPConstants.TIME_PERIOD_DISCREPANCY;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.NtpTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TOTPConfig {

  @Bean
  public TimeProvider timeProvider() throws Exception {
    return new NtpTimeProvider("pool.ntp.org");
  }

  @Bean
  public SecretGenerator secretGenerator() {
    return new DefaultSecretGenerator(SECRET_CHARACTER_LENGTH);
  }

  @Bean
  public QrGenerator qrGenerator() {
    return new ZxingPngQrGenerator();
  }

  @Bean
  public CodeGenerator codeGenerator() {
    return new DefaultCodeGenerator(HASHING_ALGO, DIGITS);
  }

  @Bean
  public CodeVerifier codeVerifier() throws Exception {
    DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator(), timeProvider());
    verifier.setTimePeriod(CODE_VALIDITY_IN_SECONDS);
    verifier.setAllowedTimePeriodDiscrepancy(TIME_PERIOD_DISCREPANCY);
    return verifier;
  }
}
