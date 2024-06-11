package SShop.mono.controler.user;

import SShop.mono.application.dto.AuthenticationResponseDTO;
import SShop.mono.application.dto.LoginDTO;
import SShop.mono.application.dto.RegisterDTO;
import SShop.mono.application.service.auth.AuthenticationSerive;
import SShop.mono.application.service.otp.OtpService;
import SShop.mono.domain.entity.postgresql.CartEntity;
import SShop.mono.domain.repository.postgrerepo.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {

  private final AuthenticationSerive authenticationSerive;
  private final OtpService otpService;

  @PostMapping("/client/register")
  public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
    try {
      AuthenticationResponseDTO authenticationResponseDTO = authenticationSerive.register(
          registerDTO);
      return ResponseEntity.ok(authenticationResponseDTO);
    } catch (Exception e) {
      return ResponseEntity.status(403).body(e.getMessage());
    }
  }

  @PostMapping("/client/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) {
    try {
      AuthenticationResponseDTO authenticationResponseDTO = authenticationSerive.authenticate(
          loginDTO);
      return ResponseEntity.ok(authenticationResponseDTO);
    } catch (Exception e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @PostMapping("/refesh-token")
  public ResponseEntity<AuthenticationResponseDTO> refeshToken(@RequestBody String refreshToken) {
    return ResponseEntity.ok(authenticationSerive.refeshToken(refreshToken));
  }

  @PostMapping("/verify")
  public ResponseEntity<?> generateOTP(
      @RequestParam(name = "userId", required = false) String userId,
      @RequestBody RegisterDTO registerDTO) {
    try {
      if (userId != null) {
        var result = otpService.generateOTP(userId);
        return ResponseEntity.ok(result);
      } else {
        var result = otpService.generateOTP(registerDTO);
        return ResponseEntity.ok(result);
      }

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/verify/{id}")
  public ResponseEntity<?> validateOTP(@PathVariable String id, @RequestBody String inputOtp) {
    try {
      var result = otpService.validateOTP(id, inputOtp);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }


}
