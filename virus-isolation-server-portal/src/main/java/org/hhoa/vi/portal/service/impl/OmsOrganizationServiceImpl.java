package org.hhoa.vi.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.mgb.dao.OmsOrganizationDao;
import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.portal.bean.OmsCreateOrganizationParam;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.mgb.dao.OmsAccountOrganizationDao;
import org.hhoa.vi.portal.service.OmsOrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Oms organization service.
 *
 * @author hhoa
 * @since 2022 /12/13
 */
@AllArgsConstructor
@Service
public class OmsOrganizationServiceImpl implements OmsOrganizationService {
    private OmsOrganizationDao omsOrganizationDao;
    private OmsAccountOrganizationDao omsAccountOrganizationDao;


    private boolean hasPermission(String accountName, Long organizationId, Long positionId) {
        return omsAccountOrganizationDao.hasPermission(accountName, organizationId);
    }

    private boolean hasAdministratorPermission(String accountName, Long organizationId) {
        return hasPermission(accountName, organizationId, 1L);
    }

    private boolean hasNormalPermission(String accountName, Long organizationId) {
        return hasPermission(accountName, organizationId, 10L);
    }

    @Override
    public void addOrganization(OmsCreateOrganizationParam organizationParam) {
        omsOrganizationDao.insert(organizationParam);
        OmsOrganization omsOrganization = omsOrganizationDao.selectOne(new QueryWrapper<>(organizationParam));
        omsAccountOrganizationDao.insertByAccountNameAndOrganizationIdAndPositionId(
                organizationParam.getUserName(), omsOrganization.getId(), 1L);
    }

    @Override
    public void deleteOrganization(Long organizationId) {
        omsOrganizationDao.deleteById(organizationId);
    }

    @Override
    public void updateOrganization(OmsOrganization organizationParam, Long organizationId) {
        omsOrganizationDao.updateById(organizationParam);
    }

    @Override
    public List<OrganizationAccount> listOrganizationAccounts(PageInfo pageInfo, Long organizationId) {
        return omsOrganizationDao.listOrganizationAccounts(organizationId, );
    }



    @Override
    public void deleteOrganizationAccountByUserId(Long organizationId, Long userId) {
        omsOrganizationDao.deleteOrganizationAccountByAccountId(organizationId, userId);
    }

    @Override
    public void updateOrganizationAccountPosition(Long organizationId, Long accountId, Long positionId) {
        omsOrganizationDao.updateOrganizationAccountPosition(organizationId, accountId, positionId);
    }
}
