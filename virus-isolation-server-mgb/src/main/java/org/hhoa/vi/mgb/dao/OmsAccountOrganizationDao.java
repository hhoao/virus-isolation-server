package org.hhoa.vi.mgb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hhoa.vi.mgb.model.AccountOrganization;
import org.hhoa.vi.mgb.model.generator.OmsAccountOrganizationRelation;

import java.util.List;

/**
 * OmsAccountOrganizationDao
 *
 * @author hhoa
 * @since 2023 /3/20
 */
public interface OmsAccountOrganizationDao extends BaseMapper<OmsAccountOrganizationRelation> {
    /**
     * Has permission boolean.
     *
     * @param accountName    the account name
     * @param organizationId the organization id
     * @return the boolean
     */
    boolean hasPermission(@Param("accountName") String accountName, @Param("organizationId") Long organizationId);

    /**
     * Insert by account name and organization id and position id.
     *
     * @param accountName the account name
     * @param id          the id
     * @param positionId  the position id
     */
    void insertByAccountNameAndOrganizationIdAndPositionId(@Param("accountName") String accountName, @Param("id") Long id, Long positionId);

    /**
     * Gets user organization.
     *
     * @param userId the user id
     * @return the user organization
     */
    List<AccountOrganization> getAccountOrganizations(@Param("userId") Long userId);
}
