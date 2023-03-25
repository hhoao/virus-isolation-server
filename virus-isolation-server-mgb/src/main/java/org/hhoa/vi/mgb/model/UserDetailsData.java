package org.hhoa.vi.mgb.model;

import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsResource;

import java.util.List;

/**
 * UmsAccountDetailsData
 *
 * @author hhoa
 * @since 2023/3/21
 **/

public class UmsAccountDetails {
    private UmsAccount account;
    private List<UmsResource> resources;
    private List<OrganizationPosition> organizationPositions;

    public UmsAccountDetails() {
    }

    public UmsAccount getAccount() {
        return account;
    }

    public void setAccount(UmsAccount account) {
        this.account = account;
    }

    public List<UmsResource> getResources() {
        return resources;
    }

    public void setResources(List<UmsResource> resources) {
        this.resources = resources;
    }

    public List<OrganizationPosition> getOrganizationPositions() {
        return organizationPositions;
    }

    public void setOrganizationPositions(List<OrganizationPosition> organizationPositions) {
        this.organizationPositions = organizationPositions;
    }
}
