package org.hhoa.vi.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * VIPortalApplication
 *
 * @author hhoa
 * @since 2023/3/18
 **/

@SpringBootApplication(scanBasePackages = {"org.hhoa.vi"})
public class VIPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(VIPortalApplication.class);
    }
}
