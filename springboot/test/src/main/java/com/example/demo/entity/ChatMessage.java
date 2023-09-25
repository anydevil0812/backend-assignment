package com.example.demo.entity;

import com.example.demo.dto.ChatMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String sender;

    @Column
    private String context;

    public static ChatMessageDTO entityToDto(ChatMessage chatMessage){
        return ChatMessageDTO.builder()
                             .sender(chatMessage.getSender())
                             .context(chatMessage.getContext())
                             .build();
    }

}