package org.hhoa.vi.mgb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hhoa.vi.mgb.model.generator.UmsAccount;

import java.util.Date;

/**
 * OrganizationAccount
 *
 * @author hhoa
 * @since 2023/3/19
 **/


public class OrganizationAccount extends UmsAccount{
    private Long positionId;

    public OrganizationAccount() {
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public OrganizationAccount(Long positionId) {
        this.positionId = positionId;
    }
}
