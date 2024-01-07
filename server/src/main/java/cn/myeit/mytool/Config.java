package cn.myeit.mytool;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 获取同级目录的配置文件
 */
@Component
@ConfigurationProperties(prefix = "frp")
public class Config {

    private Map<String, Map<String, String>> client;

    public Map<String, Map<String, String>> getClient() {
        return client;
    }

    public void setClient(Map<String, Map<String, String>> client) {
        this.client = client;
    }
}
