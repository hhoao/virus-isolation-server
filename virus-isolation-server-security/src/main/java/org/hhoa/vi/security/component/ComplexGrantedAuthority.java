package org.hhoa.vi.security.component;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serial;

/**
 * MultitypeGrantedAuthority
 *
 * @author hhoa
 * @since 2023/3/20
 **/

@Data
public class ComplexGrantedAuthority implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final String role;
    private final String type;

    public ComplexGrantedAuthority(String role, String type) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
        this.type = type;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}
