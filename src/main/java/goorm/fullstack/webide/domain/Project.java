package goorm.fullstack.webide.domain;

import goorm.fullstack.webide.dto.ProjectResponseDto;
import jakarta.persistence.*;
import lombok.*;

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
    @Column
    private String language;
    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private File rootFolder;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public ProjectResponseDto toDto() {
        return new ProjectResponseDto(id, name, detail, language, createdAt, updatedAt);
    }
}
