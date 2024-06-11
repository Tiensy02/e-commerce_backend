package SShop.mono.application.service.auth;

import SShop.mono.application.dto.AuthenticationResponseDTO;
import SShop.mono.application.dto.LoginDTO;
import SShop.mono.application.dto.RegisterDTO;
import SShop.mono.domain.entity.mongodb.UserEntity;
import SShop.mono.domain.repository.mogorepo.UserRepository;
import SShop.mono.security.Roles;
import SShop.mono.security.config.JWTService;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationSerive {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponseDTO register(RegisterDTO registerDTO) {
    UserEntity userEntity = userRepository.findByEmail(registerDTO.getEmail())
        .orElse(null);
    if (userEntity != null) {
      throw new RuntimeException("Email đã được đăng ký");
    }
    Map<String,String> notificationSetting = new HashMap<>();
    notificationSetting.put("email", registerDTO.getEmail());
    notificationSetting.put("push-notification", "");

    UserEntity user = UserEntity.builder()
        .email(registerDTO.getEmail())
        .notificationSetting(notificationSetting)
        .userPassword(passwordEncoder.encode(registerDTO.getPassword()))
        .userName(registerDTO.getUsername())
        .isSuccess(true)
        .role(Roles.USER)
        .build();

    var accessToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefeshToken(user);
    user = userRepository.save(user);
    var persmission = user.getRole().getPermissions();
    var persmissionName = persmission.stream().map(per -> per.getPermission())
        .collect(Collectors.toSet());

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    long createTime = timestamp.getTime();

    return AuthenticationResponseDTO.builder()
        .createTime(createTime)
        .userEmail(user.getEmail())
        .useName(user.getUserNameAlias())
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .userId(user.getId())
        .permissions(persmissionName)
        .notificationSetting(user.getNotificationSetting())
        .expiresIn(jwtService.getExpiration())
        .build();
  }

  public AuthenticationResponseDTO authenticate(LoginDTO login) {
    var user = userRepository.findByUserName(login.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid password");
    }

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            login.getUsername(),
            login.getPassword()
        )
    );

    var accessToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefeshToken(user);
    var permission = user.getRole().getPermissions();
    var permissionName = permission.stream().map(per -> per.getPermission())
        .collect(Collectors.toSet());

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    long createTime = timestamp.getTime();

    return AuthenticationResponseDTO.builder()
        .createTime(createTime)
        .useName(user.getUserNameAlias())
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .userId(user.getId())
        .userEmail(user.getEmail())
        .permissions(permissionName)
        .notificationSetting(user.getNotificationSetting())
        .expiresIn(jwtService.getExpiration())
        .build();
  }

  public AuthenticationResponseDTO refeshToken(String refreshToken) {
    var email = jwtService.extractEmail(refreshToken);
    var user = userRepository.findByEmail(email)
        .orElseThrow();
    if (!jwtService.isTokenValid(refreshToken, user)) {
      throw new RuntimeException("Invalid token");
    }
    var newAccessToken = jwtService.generateToken(user);
    return AuthenticationResponseDTO.builder()
        .accessToken(newAccessToken)
        .refreshToken(refreshToken)
        .build();
  }
}
