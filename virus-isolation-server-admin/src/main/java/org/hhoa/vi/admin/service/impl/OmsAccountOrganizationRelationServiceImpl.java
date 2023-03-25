package org.hhoa.vi.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.dao.OmsAccountOrganizationRelationDao;
import org.hhoa.vi.admin.service.OmsAccountOrganizationRelationService;
import org.hhoa.vi.mgb.model.generator.OmsAccountOrganizationRelation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OmsAccountOrganizationRelationServiceImpl
 *
 * @author hhoa
 * @since 2023/3/24
 **/

@Service
@AllArgsConstructor
public class OmsAccountOrganizationRelationServiceImpl implements OmsAccountOrganizationRelationService {
    OmsAccountOrganizationRelationDao accountOrganizationRelationDao;
    @Override
    public List<OmsAccountOrganizationRelation> getByOrganizationId(Long organizationId) {
        OmsAccountOrganizationRelation accountOrganizationRelation = new OmsAccountOrganizationRelation();
        accountOrganizationRelation.setOrganizationId(organizationId);
        return accountOrganizationRelationDao.selectList(new QueryWrapper<>(accountOrganizationRelation));
    }
}
