package com.example.demo.service;

import com.example.demo.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ChatMessage;

@Service
@RequiredArgsConstructor
public class RedisPubService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ChatMessageRepository chatMessageRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(ChatMessage chatMessage) throws Exception {
        if(!chatMessage.getContext().isBlank() && !chatMessage.getSender().isBlank()){
            // 소켓 통신 에러 탐지
            try {
                redisTemplate.convertAndSend("topic3", ChatMessage.entityToDto(chatMessage)); // Node 소켓으로 데이터 전송
                System.out.println("Message sent successfully - Sender : " + chatMessage.getSender() + ", Context : " + chatMessage.getContext());
            } catch (Exception e) {
                logger.error("Socket 데이터 전송 관련 Error 발생 : " + e.getMessage(), e);
            }
            // DB INSERT 에러 탐지
            try{
                chatMessageRepository.insertChatMessage(chatMessage.getSender(), chatMessage.getContext()); // DB에 데이터 저장
            } catch (Exception e) {
                logger.error("DB INSERT 관련 Error 발생 : " + e.getMessage(), e);
            }
        } else {
            throw new Exception("빈칸인 데이터가 존재합니다.");
        }
    }
}
