package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ChatMessage;
import com.example.demo.service.RedisPubService;

import lombok.RequiredArgsConstructor;
@CrossOrigin({"*"})
@RestController
@RequiredArgsConstructor
public class RedisController {
    @Autowired
    private RedisPubService redisPubService;

    @PostMapping("/api/chat")
    public String pubSub(@RequestBody ChatMessage chatMessage) throws Exception {
        //메시지 보내기
        redisPubService.sendMessage(chatMessage);
        return "success";
    }
}