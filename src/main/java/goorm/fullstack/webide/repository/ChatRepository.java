package goorm.fullstack.webide.repository;

import goorm.fullstack.webide.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

// 데이터베이스 접근을 위한 JPA 리포지토리
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {}