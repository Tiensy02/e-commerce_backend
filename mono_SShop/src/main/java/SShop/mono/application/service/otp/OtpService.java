package SShop.mono.application.service.otp;

import SShop.mono.application.dto.RegisterDTO;
import dev.samstevens.totp.exceptions.CodeGenerationException;

public interface OtpService {
  String generateOTP(String userId ) throws CodeGenerationException;
  boolean validateOTP( String userId, String inputOtp);

  String generateOTP(RegisterDTO registerDTO) throws CodeGenerationException;
}
