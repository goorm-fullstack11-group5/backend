package goorm.fullstack.webide.repository;

import goorm.fullstack.webide.domain.File;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileJpaRepository extends JpaRepository<File, Integer> {
}
