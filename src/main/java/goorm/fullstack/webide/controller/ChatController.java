package goorm.fullstack.webide.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goorm.fullstack.webide.model.ChatMessage;
import goorm.fullstack.webide.service.ChatService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;  // 채팅 관련 비즈니스 로직을 처리하는 서비스

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage handleMessage(ChatMessage message) {
        return chatService.saveMessage(message);  // 메시지를 저장하고 반환
    }

    @GetMapping("/messages")
    public List<ChatMessage> getMessages() {
        return chatService.getAllMessages();  // 저장된 모든 메시지를 반환
    }
}
