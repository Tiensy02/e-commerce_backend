package SShop.mono.security;

import static SShop.mono.security.Permissions.ADMIN_CREATE;
import static SShop.mono.security.Permissions.ADMIN_DELETE;
import static SShop.mono.security.Permissions.ADMIN_READ;
import static SShop.mono.security.Permissions.ADMIN_UPDATE;
import static SShop.mono.security.Permissions.MANAGER_CREATE;
import static SShop.mono.security.Permissions.MANAGER_DELETE;
import static SShop.mono.security.Permissions.MANAGER_READ;
import static SShop.mono.security.Permissions.MANAGER_UPDATE;
import static SShop.mono.security.Permissions.PRODUCT_CREATE;
import static SShop.mono.security.Permissions.PRODUCT_DELETE;
import static SShop.mono.security.Permissions.PRODUCT_READ;
import static SShop.mono.security.Permissions.PRODUCT_UPDATE;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor

public enum Roles {
    USER(Set.of(Permissions.USER_READ, Permissions.USER_UPDATE)),
    ADMIN(Set.of (ADMIN_READ, ADMIN_UPDATE, ADMIN_CREATE, ADMIN_DELETE, MANAGER_READ, MANAGER_UPDATE, MANAGER_CREATE, MANAGER_DELETE)),

    MANAGER(Set.of(MANAGER_READ, MANAGER_UPDATE, MANAGER_CREATE, MANAGER_DELETE)),

    PRODUCT_MANAGER(Set.of(PRODUCT_READ, PRODUCT_UPDATE, PRODUCT_CREATE, PRODUCT_DELETE));
    @Getter
    private final Set<Permissions> permissions;


    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
            .stream()
            .map(per -> new SimpleGrantedAuthority(per.getPermission()))
            .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}