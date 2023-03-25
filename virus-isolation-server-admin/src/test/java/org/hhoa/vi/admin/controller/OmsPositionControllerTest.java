package org.hhoa.vi.admin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.hhoa.vi.admin.utils.ControllerTransactionTest;
import org.hhoa.vi.admin.utils.TestUtils;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.generator.OmsPosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OmsPositionControllerTest
 *
 * @author hhoa
 * @since 2023/3/24
 **/

class OmsPositionControllerTest extends ControllerTransactionTest {

    @Test
    void list() {
        OmsPosition position = new OmsPosition();
        position.setId(1L);
        String parametersString = TestUtils.ObjectToParametersString(position);
        String forObject = getAdminVerifiedTemplate().getForObject(
                "/positions?" + parametersString, String.class);
        CommonResult<CommonPage<OmsPosition>> omsPositionCommonResult =
                TestUtils.jsonStringToObject(
                        forObject, new TypeReference<CommonResult<CommonPage<OmsPosition>>>() {
                        });
        List<OmsPosition> list = omsPositionCommonResult.getResult().getList();
        assertEquals(1, list.size());
        assertSame(1L, list.get(0).getId());
    }

//    @Test
//    void addPosition() {
//        //添加职位
//        OmsPosition position = new OmsPosition();
//        position.setName("testAddPosition");
//        CommonResult<?> commonResult = getAdminVerifiedTemplate().
//                postForObject("/positions", position, CommonResult.class);
//        Assertions.assertEquals(commonResult.getCode(), 200);
//        //查找添加的职位
//        String parametersString = TestUtils.ObjectToParametersString(position);
//        String getPosition = getAdminVerifiedTemplate().
//                getForObject(
//                        "/positions?" + parametersString, String.class);
//        CommonResult<CommonPage<OmsPosition>> omsPositionCommonResult =
//                TestUtils.jsonStringToObject(getPosition,
//                        new TypeReference<CommonResult<CommonPage<OmsPosition>>>() {
//                        });
//        List<OmsPosition> list = omsPositionCommonResult.getResult().getList();
//        Assertions.assertTrue(list.size() > 0);
//        list.forEach(omsPosition -> {
//            Assertions.assertEquals(position.getName(), omsPosition.getName());
//        });
//    }
//
//    @Test
//    @SneakyThrows
//    void updatePosition() {
//        //更新职位
//        OmsPosition position = new OmsPosition();
//        position.setId(1L);
//        position.setName("testAddPosition");
//        getAdminVerifiedTemplate().
//                put("/positions", position);
//        //查找更新的职位
//        String parametersString = TestUtils.ObjectToParametersString(position);
//        String getPosition = getAdminVerifiedTemplate().
//                getForObject(
//                        "/positions?" + parametersString, String.class);
//        CommonResult<CommonPage<OmsPosition>> omsPositionCommonResult =
//                TestUtils.jsonStringToObject(getPosition,
//                        new TypeReference<CommonResult<CommonPage<OmsPosition>>>() {
//                        });
//        List<OmsPosition> list = omsPositionCommonResult.getResult().getList();
//        Assertions.assertTrue(list.size() > 0);
//        list.forEach(omsPosition -> {
//            Assertions.assertEquals(position.getName() + position.getId(),
//                    omsPosition.getName() + position.getId());
//        });
//    }
//
//    @Test
//    void delPosition() {
//        //删除职位
//        getAdminVerifiedTemplate().
//                delete("/positions/{positionId}",
//                        Map.of("positionId", 1L));
//        //查找删除的职位
//        OmsPosition position = new OmsPosition();
//        position.setId(1L);
//        String parametersString = TestUtils.ObjectToParametersString(position);
//        String getPosition = getAdminVerifiedTemplate().
//                getForObject(
//                        "/positions?" + parametersString, String.class);
//        CommonResult<CommonPage<OmsPosition>> omsPositionCommonResult =
//                TestUtils.jsonStringToObject(getPosition,
//                        new TypeReference<CommonResult<CommonPage<OmsPosition>>>() {
//                        });
//        List<OmsPosition> list = omsPositionCommonResult.getResult().getList();
//        list.forEach(omsPosition -> {
//            Assertions.assertTrue(omsPosition.getId() != 1L);
//        });
//    }
}
