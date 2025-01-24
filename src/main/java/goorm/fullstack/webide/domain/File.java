package goorm.fullstack.webide.domain;

import goorm.fullstack.webide.dto.FileResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@Setter 엔티티의 필드값들은 불변성을 가져야 한다. 의미있는 메서드를 사용해 업데이트하도록 해야함.
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // todo: 찾아보기
public class File extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // todo: strategy마다 DBMS마다 PK 관리 방법이 달라짐.
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

    public void updatePath(String newPath) {
        // 의미를 가지도록 메서드를 작성해야함.
        // 더티체킹 방지
    }
}
