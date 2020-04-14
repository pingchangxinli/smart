package com.lee.common.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis配置文件
 *
 * @author haitao Li
 */
@Slf4j
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<Object, Object> getRedisTemplate() {
        log.info("Loader RedisTemplate [RedisConfig]");
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);
//
//        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        //template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
