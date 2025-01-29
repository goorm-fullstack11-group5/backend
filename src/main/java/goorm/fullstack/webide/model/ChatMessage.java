package goorm.fullstack.webide.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// 채팅 메시지를 위한 데이터 모델
@Entity
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    private Long userId;
    private String message;
    private LocalDateTime chatAt;
}