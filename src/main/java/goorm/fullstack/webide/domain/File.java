package goorm.fullstack.webide.domain;

import goorm.fullstack.webide.dto.FileResponseDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn
    @ManyToOne(optional = false)
    private Project project;
    @Column
    @OneToMany(
        mappedBy = "parent",
        cascade = CascadeType.REMOVE,
        orphanRemoval = true,
        fetch = FetchType.EAGER) // todo: EAGER로 설정했을 때 성능 손실이 있는지?
    private List<File> files;
    @JoinColumn
    @ManyToOne
    private File parent;

    public FileResponseDto toDto() {
        return new FileResponseDto(id, name, content, createdAt, updatedAt);
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void rename(String name) {
        this.name = name;
    }
}
