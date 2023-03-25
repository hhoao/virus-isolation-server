package org.hhoa.vi.mgb.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.mgb.model.generator.UmsAccount;

import java.util.List;

/**
 * The interface OmsOrganizationDao
 *
 * @author hhoa
 * @since 2022 /12/12
 */
public interface OmsOrganizationDao extends BaseMapper<OmsOrganization> {
    /**
     * Update organization account position.
     *
     * @param organizationId the organization id
     * @param accountId      the account id
     * @param positionId     the position id
     */
    void updateOrganizationAccountPosition(
            @Param("organizationId") Long organizationId,
            @Param("accountId") Long accountId,
            @Param("positionId") Long positionId);

    /**
     * Delete organization account by account id.
     *
     * @param organizationId the organization id
     * @param accountId      the account id
     */
    void deleteOrganizationAccountByAccountId(@Param("organizationId") Long organizationId, @Param("accountId") Long accountId);

    /**
     * List organization accounts list.
     *
     * @param organizationId the organization id
     * @param accountParam
     * @return the list
     */
    List<OrganizationAccount> listOrganizationAccounts(
            @Param("organizationId") Long organizationId,
            @Param("accountParam") UmsAccount accountParam);


    /**
     * Delete by account name and organization id.
     *
     * @param accountId      the account name
     * @param organizationId the organization id
     */
    void deleteByAccountIdAndOrganizationId(@Param("accountId") Long accountId, @Param("organizationId") Long organizationId);
}
