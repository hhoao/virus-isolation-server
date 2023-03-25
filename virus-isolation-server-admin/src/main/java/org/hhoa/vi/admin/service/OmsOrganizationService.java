package org.hhoa.vi.admin.service;

import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.mgb.model.generator.UmsAccount;

import java.util.List;

/**
 * The interface Oms organization service.
 *
 * @author hhoa
 * @since 2022 /12/13
 */
public interface OmsOrganizationService {
    /**
     * List list.
     *
     * @param organizationParams the organization params
     * @param pageInfo           the page info
     * @return the list
     */
    List<OmsOrganization> list(OmsOrganization organizationParams, PageInfo pageInfo);

    /**
     * Add organization.
     *
     * @param organizationParam the organization param
     */
    void addOrganization(OmsOrganization organizationParam);

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
     */
    void updateOrganization(OmsOrganization organizationParam);

    /**
     * List organization members list.
     *
     * @param pageInfo       the page info
     * @param organizationId the organization name
     * @param accountParam
     * @return the list
     */
    List<OrganizationAccount> listOrganizationAccounts(PageInfo pageInfo, Long organizationId, UmsAccount accountParam);

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

    void deleteByAccountIdAndOrganizationId(Long accountId, Long organizationId);
}
