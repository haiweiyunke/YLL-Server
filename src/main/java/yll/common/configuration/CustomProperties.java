package yll.common.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 自定义属性
 * @author YYL
 */
@Component
//@ConfigurationProperties(prefix = "custom.properties")
@Order(0)
@Data
public class CustomProperties {

    /** 用户默认密码 */
    private String defaultUserPassword = "123456";

    /** app用户默认密码 */
    @Value("${default.password}")
    private String defaultCustomerPassword = "111111";

    /** 文件存储目录 */
    @Value("${file.store.directory}")
    private String fileStoreDirectory;

}

