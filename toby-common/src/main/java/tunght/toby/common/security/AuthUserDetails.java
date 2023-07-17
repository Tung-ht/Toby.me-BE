package tunght.toby.common.security;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tunght.toby.common.entity.RoleEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AuthUserDetails implements UserDetails {
    private final Long id;
    private final String email;
    private final Set<RoleEntity> authorities;

    @Builder
    public AuthUserDetails(Long id, String email, Set<RoleEntity> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var grantedAuthorities = new HashSet<GrantedAuthority>();
        for (RoleEntity role: this.authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
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
}
