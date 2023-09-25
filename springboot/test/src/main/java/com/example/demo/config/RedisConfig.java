package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.demo.entity.ChatMessage;

@Configuration
public class RedisConfig {
	
	@Value("${spring.redis.host}")
	private String host;

    @Value("${spring.redis.port}")
    private int port;
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
	 	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	 	redisStandaloneConfiguration.setHostName(host);
	 	redisStandaloneConfiguration.setPort(port);
	 	LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);

        return lettuceConnectionFactory;
    }
    
    //redisTemplate 설정 (직렬화, 역직렬화)
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));
        return redisTemplate;
    }

    // 밑 부분은 websocket이 아닌 Spring Boot에서의 sub에 대한 설정!
    // ListenerAdapter 설정
//    @Bean
//    MessageListenerAdapter messageListenerAdapter() {
//        return new MessageListenerAdapter(new RedisSubService());
//    } // MessageListener를 implements한 RedisSubService를 파라미터로 입력

    // pub/sub 토픽 설정
//    @Bean
//    ChannelTopic topic() {
//        return new ChannelTopic("topic2");
//    }

    // 컨테이너 설정 => ListenerAdapter와 ChannelTopic으로 redis pub/sub 메시지를 처리하는 Listener 생성
//    @Bean
//    RedisMessageListenerContainer redisContainer() {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(redisConnectionFactory());
//        container.addMessageListener(messageListenerAdapter(), topic());
//        return container;
//    }

}
