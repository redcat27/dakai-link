package www.dakai.link.common.httpclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: IotSeviceConfig
 * @Description:
 * @Author: jiangdakai
 * @Since: 2022/3/24 2:43 下午
 * @Version
 */
@Configuration
@ConfigurationProperties(prefix = "iot.service")
@Data
public class IotServiceConfig {
    private String url;
}
