package org.hhoa.vi.portal.impl;


import org.hhoa.vi.mgb.model.generator.AmsArticle;
import org.hhoa.vi.portal.TransactionTest;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.service.AmsArticleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * AmsArticleServiceImplTest.
 *
 * @author hhoa
 * @since 2022/12/13
 **/



@SpringBootTest
class AmsArticleServiceImplTest extends TransactionTest {
    @Autowired
    private AmsArticleService articleService;

    @Test
    void list() {
        AmsArticle article = new AmsArticle();
        articleService.list(article, new PageInfo(1, 5));
    }

    @Test
    void selectById() {
        AmsArticle article = new AmsArticle();
        List<AmsArticle> list = articleService.list(article, new PageInfo(1, 5));
        for (AmsArticle articleItem : list) {
            System.out.println(articleItem);
            AmsArticle amsArticle = articleService.selectById(articleItem.getId());
            Assertions.assertNotNull(amsArticle);
        }
    }
}
