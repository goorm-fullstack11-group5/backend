package goorm.fullstack.webide.service;

import java.util.List;
import org.springframework.stereotype.Service;
import goorm.fullstack.webide.model.ChatMessage;
import goorm.fullstack.webide.repository.ChatRepository;

@Service  // 스프링의 서비스 계층 컴포넌트임을 나타냄
public class ChatService {
    private final ChatRepository chatRepository;  // 채팅 데이터 처리를 위한 리포지토리

    // 생성자 주입을 통한 ChatRepository 의존성 주입
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<ChatMessage> getAllMessages() {
        return chatRepository.findAll();
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return chatRepository.save(message);
    }
}
