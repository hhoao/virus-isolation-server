package org.hhoa.vi.admin.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置文件.
 *
 * @author hhoa
 * @since 2022/5/5
 **/
@Configuration
@MapperScan(basePackages = {"org.hhoa.vi.admin.dao", "org.hhoa.vi.mgb.dao"})
public class AdminMyBatisConfig {
}
