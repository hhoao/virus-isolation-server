package org.hhoa.vi.portal.service;

import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.portal.bean.OmsCreateOrganizationParam;
import org.hhoa.vi.portal.bean.PageInfo;

import java.util.List;

/**
 * The interface Oms organization service.
 *
 * @author hhoa
 * @since 2022 /12/13
 */
public interface OmsOrganizationService {

    /**
     * Add organization.
     *
     * @param organizationParam the organization param
     */
    void addOrganization(OmsCreateOrganizationParam organizationParam);

    /**
     * Delete organization.
     *
     * @param organizationId the organization id
     */
    void deleteOrganization(Long organizationId);

    /**
     * Update organization.
     *
     * @param organizationParam the organization param
     * @param organizationId
     */
    void updateOrganization(OmsOrganization organizationParam, Long organizationId);

    /**
     * List organization members list.
     *
     * @param pageInfo       the page info
     * @param organizationId the organization name
     * @return the list
     */
    List<OrganizationAccount> listOrganizationAccounts(PageInfo pageInfo, Long organizationId);

    /**
     * Delete organization account by username.
     *
     * @param organizationId the organization name
     * @param userId         the username
     */
    void deleteOrganizationAccountByUserId(Long organizationId, Long userId);

    /**
     * Update organization account position.
     *
     * @param organizationId the organization name
     * @param accountId      the username
     * @param positionId     the position id
     */
    void updateOrganizationAccountPosition(Long organizationId, Long accountId, Long positionId);
}
