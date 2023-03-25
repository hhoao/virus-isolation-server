package org.hhoa.vi.admin.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hhoa.vi.mgb.model.OrganizationPosition;
import org.hhoa.vi.mgb.model.UserDetailsData;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.security.component.ComplexGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 认证需要的UserDetails.
 *
 * @author hhoa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UmsAccountDetails implements UserDetails, Serializable  {

    @Serial
    private static final long serialVersionUID = 1L;
    private UmsAccount account;
    private List<UmsResource> resources;
    private List<OrganizationPosition> organizationPositions;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //最终roleId:roleName即为访问资源所需要的权限, factoryId:jobId为访问公司所需要的权限
        List<GrantedAuthority> list = new ArrayList<>();
        getResources()
                .forEach(resource -> list.add(
                        new ComplexGrantedAuthority(
                                resource.getId() + ":" + resource.getName(), "role")));
        getOrganizationPositions().forEach(
                organizationPosition -> list.add(
                        new ComplexGrantedAuthority(
                                organizationPosition.getOrganizationId()+":" + organizationPosition.getPositionId(), "position")));
        return list;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return "";
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return "";
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return getAccount().getStatus();
    }
}
