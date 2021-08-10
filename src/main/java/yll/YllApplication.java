package yll;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * 应用程序入口类
 * @author YYL
 */
@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class YllApplication {
    /**
     * 应用入口
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(YllApplication.class, args);
        log.info("[Startup Success]");
    }
}
