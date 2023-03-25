package org.hhoa.vi.admin.service;

import org.hhoa.vi.mgb.model.generator.OmsAccountOrganizationRelation;

import java.util.List;

/**
 * OmsAccountOrganizationRelationService
 *
 * @author hhoa
 * @since 2023/3/24
 **/

public interface OmsAccountOrganizationRelationService {
    List<OmsAccountOrganizationRelation> getByOrganizationId(Long organizationId);
}
