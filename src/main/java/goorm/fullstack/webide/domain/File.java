package goorm.fullstack.webide.domain;

import goorm.fullstack.webide.dto.FileResponseDto;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String content;
    @Column
    private Boolean isFolder;
    @Column
    @OneToMany
    private List<File> files;

    public FileResponseDto toDto() {
        return new FileResponseDto(id, name, content, createdAt, updatedAt);
    }

    public boolean isFolder() {
        return isFolder;
    }
}
