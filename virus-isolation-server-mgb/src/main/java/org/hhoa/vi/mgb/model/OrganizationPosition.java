package org.hhoa.vi.mgb.model;

import java.io.Serializable;

/**
 * OrganizationPosition
 *
 * @author hhoa
 * @since 2023/3/20
 **/

public class OrganizationPosition implements Serializable {
    private long organizationId;
    private long positionId;

    public OrganizationPosition(long organizationId, long positionId) {
        this.organizationId = organizationId;
        this.positionId = positionId;
    }

    public OrganizationPosition() {
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }
}
