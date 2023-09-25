package com.example.demo.service;

import com.example.demo.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ChatMessage;

@Service
@RequiredArgsConstructor
public class RedisPubService {

    @Autowired
    ChatMessageRepository chatMessageRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(ChatMessage chatMessage) {
        System.out.println("topic send");
        redisTemplate.convertAndSend("topic3", ChatMessage.entityToDto(chatMessage)); // Node 소켓으로 데이터 전송
        System.out.println("Message sent successfully - Sender : " + chatMessage.getSender() + ", Context : " + chatMessage.getContext());
        chatMessageRepository.insertChatMessage(chatMessage.getSender(), chatMessage.getContext()); // DB에 데이터 저장
    }
}
