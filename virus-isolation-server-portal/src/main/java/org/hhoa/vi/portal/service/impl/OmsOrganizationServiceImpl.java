package org.hhoa.vi.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.OrganizationAccount;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.dao.OmsOrganizationDao;
import org.hhoa.vi.admin.service.OmsOrganizationService;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
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
public class OmsOrganizationServiceImpl  implements OmsOrganizationService {
    private OmsOrganizationDao omsOrganizationDao;

    @Override
    public List<OmsOrganization> list(OmsOrganization organizationParams, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        QueryWrapper<OmsOrganization> queryWrapper = new QueryWrapper<>();
        return omsOrganizationDao.selectList(queryWrapper);
    }

    @Override
    public void addOrganization(OmsOrganization organizationParam) {
        omsOrganizationDao.insert(organizationParam);
    }

    @Override
    public void deleteOrganization(Long organizationId) {
        omsOrganizationDao.deleteById(organizationId);
    }

    @Override
    public void updateOrganization(OmsOrganization organizationParam) {
        omsOrganizationDao.updateById(organizationParam);
    }

    @Override
    public List<OrganizationAccount> listOrganizationAccounts(PageInfo pageInfo, Long organizationId) {
        PageHelper.startPage(pageInfo);
        return omsOrganizationDao.listOrganizationAccounts(organizationId);
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
