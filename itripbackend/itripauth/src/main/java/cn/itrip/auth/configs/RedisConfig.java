package cn.itrip.auth.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.security.cert.TrustAnchor;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperty redisProperty;


    /**
     * 获取JedisPool连接池
     */
    @Bean
    public JedisPool getJedisPool(){

        JedisPool jedisPool = null;

        try {
            // 1. 创建一个连接池配置对象
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxActive(redisProperty.getMaxActive());
            config.setMaxIdle(redisProperty.getMaxIdle());
            config.setMaxWait(redisProperty.getMaxWait());
            config.setTestOnBorrow(true);    // 是不是作为容器
            // 2. JedisPool对象
            jedisPool = new JedisPool(config, redisProperty.getHost(), redisProperty.getPort());
            // 3. 返回JedisPool对象
            return jedisPool;
        }catch (Exception e){
            e.printStackTrace();
            return jedisPool;
        }


    }
}
