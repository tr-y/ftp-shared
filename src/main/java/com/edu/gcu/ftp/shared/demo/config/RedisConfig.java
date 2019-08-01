package com.edu.gcu.ftp.shared.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

  @Bean
  public KeyGenerator keyGenerator(){

    return ((Object target,Method method,Object... params)-> {
      StringBuilder sb = new StringBuilder();
      sb.append(target.getClass().getName());
      sb.append(method.getClass().getName());
      for (Object o : params) {
        sb.append(o.toString());
      }
      return sb.toString();
    });
  }


  @Bean
  public CacheManager cacheManager(RedisConnectionFactory factory){
    RedisCacheConfiguration rcm = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1));
    return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                            .cacheDefaults(rcm)
                            .build();
  }



  @Bean
  public RedisTemplate<String,String>redisTemplate(RedisConnectionFactory connectionFactory){
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(connectionFactory);
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =new Jackson2JsonRedisSerializer(Object.class);
    //GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(Object.class);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL,Visibility.ANY);
    objectMapper.enableDefaultTyping();
    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    stringRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    stringRedisTemplate.afterPropertiesSet();
    return stringRedisTemplate;
  }






}
