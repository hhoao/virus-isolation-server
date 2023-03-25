package org.hhoa.vi.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * VIAdminAppliation
 *
 * @author hhoa
 * @since 2023/3/18
 **/

@SpringBootApplication(scanBasePackages = {"org.hhoa.vi"})
public class VIAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(VIAdminApplication.class);
    }
}

