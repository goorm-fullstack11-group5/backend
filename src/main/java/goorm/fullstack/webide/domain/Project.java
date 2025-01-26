package goorm.fullstack.webide.domain;

import goorm.fullstack.webide.dto.ProjectResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String detail;
    @JoinColumn
    @OneToOne
    private File rootFolder;

    public ProjectResponseDto toDto() {
        return new ProjectResponseDto(id, name, detail, createdAt, updatedAt);
    }

    public void updateRootFolder(File rootFolder) {
        this.rootFolder = rootFolder;
    }
}
