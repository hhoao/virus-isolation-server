package org.hhoa.vi.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.service.OmsAccountOrganizationRelationService;
import org.hhoa.vi.admin.service.UmsAccountService;
import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.dao.OmsOrganizationDao;
import org.hhoa.vi.admin.service.OmsOrganizationService;
import org.hhoa.vi.mgb.model.OrganizationPosition;
import org.hhoa.vi.mgb.model.generator.OmsAccountOrganizationRelation;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    private OmsAccountOrganizationRelationService accountOrganizationRelationService;
    private UmsAccountService accountService;

    @Override
    public List<OmsOrganization> list(OmsOrganization organizationParams, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        QueryWrapper<OmsOrganization> queryWrapper = new QueryWrapper<>(organizationParams);
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
    public List<OrganizationAccount> listOrganizationAccounts(
            PageInfo pageInfo, Long organizationId, UmsAccount accountParam) {
        PageHelper.startPage(pageInfo);
        List<OmsAccountOrganizationRelation> byOrganizationId =
                accountOrganizationRelationService.getByOrganizationId(organizationId);
        List<OrganizationAccount> accounts = new ArrayList<>();
        if (accountParam != null && accountParam.getId() != null) {
            UmsAccount accounts1 = accountService.getAccounts(accountParam.getId());
            OrganizationAccount organizationAccount = new OrganizationAccount();
            BeanUtils.copyProperties(accounts1, organizationAccount);
            return List.of(organizationAccount);
        }
        byOrganizationId.forEach(relation -> {
            if (accountParam != null) {
                accountParam.setId(relation.getAccountId());
            }
            List<UmsAccount> account = accountService.getAccount(accountParam);
            if (account.size() != 0){
                OrganizationAccount organizationAccount = new OrganizationAccount();
                BeanUtils.copyProperties(account.get(0), organizationAccount);
                organizationAccount.setPositionId(relation.getPositionId());
                accounts.add(organizationAccount);
            }
        });
        return accounts;
    }

    @Override
    public void deleteOrganizationAccountByUserId(Long organizationId, Long userId) {
        omsOrganizationDao.deleteOrganizationAccountByAccountId(organizationId, userId);
    }

    @Override
    public void updateOrganizationAccountPosition(Long organizationId, Long accountId, Long positionId) {
        omsOrganizationDao.updateOrganizationAccountPosition(organizationId, accountId, positionId);
    }


    @Override
    public void deleteByAccountIdAndOrganizationId(Long accountId, Long organizationId) {
        omsOrganizationDao.deleteByAccountIdAndOrganizationId(accountId, organizationId);
    }
}
