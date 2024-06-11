package SShop.mono.security;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public enum Permissions {

  // ADMIN permissions
  ADMIN_READ("admin:read"),
  ADMIN_UPDATE("admin:update"),
  ADMIN_CREATE("admin:create"),
  ADMIN_DELETE("admin:delete"),

  // Manager permissions
  MANAGER_READ("management:read"),
  MANAGER_UPDATE("management:update"),
  MANAGER_CREATE("management:create"),
  MANAGER_DELETE("management:delete"),

  // Procduct permissions
  PRODUCT_READ("product:read"),
  PRODUCT_UPDATE("product:update"),
  PRODUCT_CREATE("product:create"),
  PRODUCT_DELETE("product:delete"),

  // User permissions
  USER_READ("user:read"),
  USER_UPDATE("user:update"),
  USER_CREATE("user:create"),
  USER_DELETE("user:delete");

  private final String permission;

  public String getPermission() {
    return permission;
  }
}
