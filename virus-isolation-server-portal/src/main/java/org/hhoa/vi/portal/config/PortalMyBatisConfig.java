package org.hhoa.vi.portal.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置文件.
 *
 * @author hhoa
 * @since 2022/5/5
 **/
@Configuration
@MapperScan(basePackages = {"org.hhoa.vi.portal.dao", "org.hhoa.vi.mgb.mapper"})
public class PortalMyBatisConfig {
}
