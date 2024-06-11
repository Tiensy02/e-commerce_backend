package SShop.mono.domain.entity.mongodb;

import SShop.mono.domain.entity.postgresql.BaseEntity;
import SShop.mono.domain.entity.postgresql.CartEntity;
import SShop.mono.domain.model.UserConnect;
import SShop.mono.domain.utils.SecurityUtils;
import SShop.mono.security.Roles;
import jakarta.persistence.PrePersist;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Data
@Builder
@Document(collection = "users")
public class UserEntity extends BaseEntity implements UserDetails {
  @Id
  private String id;

  @Field(name = "user_name")
  private String userName;

  @Field(name = "user_password")
  private String userPassword;

  @Email(message = "Email should be valid")
  private String  email;

  private List<CartEntity> carts;

  @Pattern(regexp = "^\\d{10,}$", message = "Phone should be valid")
  private String phone;

  private String address;

  private Roles role;

  private String avatar;

  private Set<UserConnect> usersConnected ;

  private Map<String,String> notificationSetting;

  private boolean isSuccess;

  private String otp;

  private Instant otpExpiration;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return userPassword;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void addUserConnected(UserConnect userConnectId) {
    if (usersConnected == null ) {
      usersConnected = new HashSet<>();
    }
    this.usersConnected.add(userConnectId);
  }

  @PrePersist
  public void prePersist() {
    this.setCreatedDate(LocalDateTime.now());
    this.setCreatedBy(SecurityUtils.getCurrentUsername());
  }
  public String getUserNameAlias() {
    return userName;
  }

}