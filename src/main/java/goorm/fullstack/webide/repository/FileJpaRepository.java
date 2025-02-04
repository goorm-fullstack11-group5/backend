package goorm.fullstack.webide.repository;

import goorm.fullstack.webide.domain.File;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileJpaRepository extends JpaRepository<File, Integer> {

    // 특정 폴더 하위에 있는 모든 파일과 폴더를 리스트로 반환하는 NativeQuery 함수
    @Query(value = """
        WITH RECURSIVE file_tree AS (
            SELECT id, name, is_folder, content, parent_id, created_at, updated_at, user_id FROM file WHERE id = ?1
            UNION ALL
            SELECT f.id, f.name, f.is_folder, f.content, f.parent_id, f.created_at, f.updated_at, f.user_id FROM file f 
            INNER JOIN file_tree ft ON f.parent_id = ft.id
        ) SELECT * FROM file_tree
        """, nativeQuery = true)
    List<File> findAllFilesInFolder(int id);
}