package org.hhoa.vi.admin.utils;

import com.zaxxer.hikari.HikariDataSource;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

/**
 * ControllerTransactionTest
 * 使用APO监听controller方法再进行回滚，只能回滚单个Controller访问
 *
 * @author hhoa
 * @since 2023/3/24
 **/

@Configuration
@Import(ControllerTransactionTest.TransactionTestAspect.class)
public class ControllerTransactionTest extends BasicTest {
    @Autowired
    PlatformTransactionManager transactionManager;


    /**
     * The type Web log aspect.
     */
    @Aspect
    @Component
    @Order(1)
    public static class TransactionTestAspect {
        private TransactionStatus transactionStatus;
        @Autowired
        DataSource dataSource;
        @Autowired
        PlatformTransactionManager transactionManager;
        @Autowired
        TransactionDefinition transactionDefinition;
        /**
         * Web log.
         */
        @Pointcut("execution(public * *.*.*.*.controller.*(..)))")
        public void webLog() {
        }

        /**
         * Do before.
         */
        @Before("webLog()")
        public void doBefore() {
            DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
            defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
            defaultTransactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
            transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        }

        @After("webLog()")
        public void doAfter() {
            transactionManager.rollback(transactionStatus);
        }
    }
}
