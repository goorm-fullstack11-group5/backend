package goorm.fullstack.webide.config;

import goorm.fullstack.webide.repository.FileJpaRepository;
import goorm.fullstack.webide.service.CodeRunner;
import goorm.fullstack.webide.service.DatabaseFileSystemService;
import goorm.fullstack.webide.service.FileService;
import goorm.fullstack.webide.service.FileSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FileFolderConfig {
    private final FileJpaRepository fileJpaRepository;
    private final CodeRunner codeRunner;

    @Bean
    public FileSystemService fileSystemService() {
        return new DatabaseFileSystemService(fileJpaRepository, codeRunner);
    }
}
