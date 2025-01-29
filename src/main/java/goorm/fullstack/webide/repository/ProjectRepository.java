package goorm.fullstack.webide.repository;

import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findAllByUser(User user);
}
