package SShop.mono.application.dto.user;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDto {
  private String userNameAlias;

  private String avatar;

}
