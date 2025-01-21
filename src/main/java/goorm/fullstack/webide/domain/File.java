package goorm.fullstack.webide.domain;

import goorm.fullstack.webide.dto.FileResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String path;
    @Column
    private String name;
    @Column
    private String content;
    @Column
    private Boolean isFolder;

    public FileResponseDto toDto() {
        return new FileResponseDto(id, path, name, content, createdAt, updatedAt);
    }

    public boolean isFolder() {
        return isFolder;
    }
}
