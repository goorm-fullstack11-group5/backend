package goorm.fullstack.webide.repository;

import goorm.fullstack.webide.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FileJpaRepository extends JpaRepository<File, Integer> {
    void deleteByPath(String path);
    Optional<File> findByPath(String path);

    @Modifying
    @Query(value = "delete from file f where f.path like ?1%", nativeQuery = true)
    void deleteInPathStartingWith(@Param("prefix") String path);
}
