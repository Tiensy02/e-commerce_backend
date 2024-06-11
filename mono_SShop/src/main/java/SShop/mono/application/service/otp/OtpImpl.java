package SShop.mono.application.service.otp;

import static SShop.mono.application.service.otp.TOTPConstants.CODE_VALIDITY_IN_SECONDS;

import SShop.mono.application.dto.RegisterDTO;
import SShop.mono.application.dto.notification.TargetNotification;
import SShop.mono.application.service.auth.AuthenticationSerive;
import SShop.mono.controler.client.NotificationClient;
import SShop.mono.domain.entity.mongodb.UserEntity;
import SShop.mono.domain.repository.mogorepo.UserRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import jakarta.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class OtpImpl implements OtpService {

  @Autowired
  private CodeGenerator codeGenerator;

  @Autowired
  private UserRepository userRepository;

  @Value("${application.otp.secret}")
  private String secretCode;

  @Autowired
  private NotificationClient notificationClient;

  @Autowired
  private AuthenticationSerive authenticationSerive;


  @Override
  public String generateOTP(String userId) throws CodeGenerationException {

    UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException());

    TOTPCode totpCode = new TOTPCode();
    totpCode.setCode(codeGenerator.generate(secretCode,
        Instant.now().getEpochSecond() / CODE_VALIDITY_IN_SECONDS));
    Instant expiryTime = Instant.now().plusSeconds(CODE_VALIDITY_IN_SECONDS);

    user.setOtp(totpCode.code);
    user.setOtpExpiration(expiryTime);

    Map<String, String> targets = new HashMap<>();
    targets.put(user.getId(), user.getEmail());

    TargetNotification targetNotification = createNotificationMFA(totpCode.code, targets);
    notificationClient.sendNotification(targetNotification);
    userRepository.save(user);
    return userId;
  }

  @Override
  public boolean validateOTP(String userId, String inputOtp) {
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));

    String codeUser = user.getOtp();
    Instant timeExpiration = user.getOtpExpiration();
    Instant now = Instant.now();
    if (now.isBefore(timeExpiration) && codeUser.equals(inputOtp)) {
      if(!user.isSuccess()) {
        userRepository.delete(user);
        RegisterDTO registerDTO = RegisterDTO.builder()
            .username(user.getUserNameAlias())
            .password(user.getPassword())
            .email(user.getEmail())
            .build();
        authenticationSerive.register(registerDTO);
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public String generateOTP(RegisterDTO registerDTO) throws CodeGenerationException {
    UserEntity userEntity = userRepository.findByEmail(registerDTO.getEmail())
        .orElse(null);
    if (userEntity != null) {
      throw new RuntimeException("Email đã được đăng ký");
    }

    TOTPCode totpCode = new TOTPCode();
    totpCode.setCode(codeGenerator.generate(secretCode,
        Instant.now().getEpochSecond() / CODE_VALIDITY_IN_SECONDS));
    Instant expiryTime = Instant.now().plusSeconds(CODE_VALIDITY_IN_SECONDS);

    UserEntity user = UserEntity.builder()
        .userName(registerDTO.getUsername())
        .userPassword(registerDTO.getPassword())
        .email(registerDTO.getEmail())
        .otp(totpCode.code)
        .isSuccess(false)
        .otpExpiration(expiryTime)
        .build();

    user = userRepository.save(user);

    Map<String, String> targets = new HashMap<>();
    targets.put(user.getId(), registerDTO.getEmail());
    TargetNotification targetNotification = createNotificationMFA(totpCode.getCode(), targets);

    notificationClient.sendNotification(targetNotification);
    return user.getId();
  }

  @Getter
  @Setter
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class TOTPCode {

    String code;
    boolean verificationStatus;
  }

  private TargetNotification createNotificationMFA(String code, Map<String, String> targets) {
    TargetNotification targetNotification = TargetNotification.builder()
        .content("Mã xác thực của bạn là: " + code)
        .title("Xác minh tài khoản")
        .targetIds(targets)
        .notificationType("product")
        .settingName("email")
        .build();
    return targetNotification;
  }

}
