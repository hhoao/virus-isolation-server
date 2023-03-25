package org.hhoa.vi.admin.service.impl;

import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.service.OmsPositionService;
import org.hhoa.vi.admin.utils.ServiceTransactionTest;
import org.hhoa.vi.mgb.model.generator.OmsPosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * OmsPositionServiceImplTest
 *
 * @author hhoa
 * @since 2023/3/19
 **/

class OmsPositionServiceImplTest extends ServiceTransactionTest {

    @Autowired
    private OmsPositionService positionService;

    @Test
    void list() {
        OmsPosition amsPosition = new OmsPosition();
        amsPosition.setName("管理员");
        List<OmsPosition> list = positionService.list(amsPosition, new PageInfo(1, 0));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void addPosition() {
        OmsPosition amsPosition = new OmsPosition();
        Assertions.assertThrows(Exception.class, () -> positionService.addPosition(amsPosition));
        amsPosition.setName("testPosition");
        positionService.addPosition(amsPosition);
        List<OmsPosition> list = positionService.list(null, new PageInfo(1, 0));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void deletePosition() {
        OmsPosition amsPosition = new OmsPosition();
        amsPosition.setName("testPosition");
        positionService.addPosition(amsPosition);
        List<OmsPosition> list = positionService.list(null, new PageInfo(1, 0));
        OmsPosition amsPosition1 = list.get(0);
        positionService.deletePosition(amsPosition1.getId());
        Assertions.assertThrows(Exception.class, () -> positionService.addPosition(new OmsPosition()));
    }

    @Test
    void updatePosition() {
        OmsPosition omsPosition = new OmsPosition();
        omsPosition.setId(1L);
        omsPosition.setName("testPosition");
        positionService.updatePosition(omsPosition);
        List<OmsPosition> list = positionService.list(omsPosition, new PageInfo(1, 5));
        Assertions.assertTrue(list.size() > 0);
    }
}
