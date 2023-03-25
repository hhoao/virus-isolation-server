package org.hhoa.vi.admin.service.impl;


import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.service.AmsTagService;
import org.hhoa.vi.admin.utils.ServiceTransactionTest;
import org.hhoa.vi.mgb.model.generator.AmsTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * AmsTagServiceImplTest.
 *
 * @author hhoa
 * @since 2022/12/20
 **/

class AmsTagServiceImplTest extends ServiceTransactionTest {
    @Autowired
    AmsTagService tagService;

    @Test
    void list() {
        AmsTag amsTag = new AmsTag();
        amsTag.setLabel("testLabel");
        tagService.addTag(amsTag);
        List<AmsTag> list = tagService.list(null, new PageInfo(1, 0));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void addTag() {
        AmsTag amsTag = new AmsTag();
        Assertions.assertThrows(Exception.class, () -> tagService.addTag(amsTag));
        amsTag.setLabel("testLabel");
        tagService.addTag(amsTag);
        List<AmsTag> list = tagService.list(null, new PageInfo(1, 0));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void deleteTag() {
        AmsTag amsTag = new AmsTag();
        amsTag.setLabel("testLabel");
        tagService.addTag(amsTag);
        List<AmsTag> list = tagService.list(null, new PageInfo(1, 0));
        AmsTag amsTag1 = list.get(0);
        tagService.deleteTag(amsTag1.getId());
    }

    @Test
    void updateTag() {
    }
}
