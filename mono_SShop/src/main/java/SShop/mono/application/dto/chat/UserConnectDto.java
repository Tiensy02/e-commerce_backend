package SShop.mono.application.dto.chat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserConnectDto {

  private String userConnectingId;

  private String userConnectedId;

}
