package cn.itrip.auth.configs;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis")  // 前缀
@PropertySource("database.properties")  // 引入外部的属性配置文件
public class RedisProperty {

    private String host;
    private int port;
    private int defaultdb;
    private int timeout;
    private int maxActive;
    private int maxIdle;
    private int maxWait;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDefaultdb() {
        return defaultdb;
    }

    public void setDefaultdb(int defaultdb) {
        this.defaultdb = defaultdb;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    /*
    该类的属性和外部配置文件中的配置要一致，如下所示：
    redis.host=127.0.0.1
    redis.port=6379
    redis.default.db=0
    redis.timeout=3000
    redis.maxActive=300
    redis.maxIdle=100
    redis.maxWait=1000
*/


}
