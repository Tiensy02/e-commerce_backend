package SShop.mono.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationResponseDTO {
  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("refresh_token")
  private String refreshToken;

  private String useName;

  private String userId;

  private Set<String> permissions;

  private Map<String,String> notificationSetting;

  @JsonProperty("expires_in")
  private long expiresIn;

  private long createTime;

  private String userEmail;
}
