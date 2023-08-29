package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ChatMessage;
import com.example.demo.service.RedisPubService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RedisController {
    @Autowired
    private RedisPubService redisPubService;

    @PostMapping("/api/chat")
    public String pubSub(@RequestBody ChatMessage chatMessage) {
        //메시지 보내기
        redisPubService.sendMessage(chatMessage);

        return "success";
    }
}