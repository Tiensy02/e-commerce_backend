package SShop.mono.domain.model;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class UserConnect {

  private String userId;

  private String avatar;

  private String userName;

  public UserConnect(String userId, String avatar, String userName) {
    this.userId = userId;
    this.avatar = avatar;
    this.userName = userName;
  }
}
