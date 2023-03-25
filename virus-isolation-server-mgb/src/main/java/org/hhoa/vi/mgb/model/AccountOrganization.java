package org.hhoa.vi.mgb.model;

import org.hhoa.vi.mgb.model.generator.OmsOrganization;

/**
 * AccountOrgainzation
 *
 * @author hhoa
 * @since 2023/3/21
 **/

public class AccountOrganization extends OmsOrganization {
    private Long positionId;

    public AccountOrganization() {
    }

    public AccountOrganization(Long positionId) {
        this.positionId = positionId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }
}
