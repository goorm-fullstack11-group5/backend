package goorm.fullstack.webide.config;

import goorm.fullstack.webide.repository.FileJpaRepository;
import goorm.fullstack.webide.service.CodeRunner;
import goorm.fullstack.webide.service.DatabaseFileFolderService;
import goorm.fullstack.webide.service.FileFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FileFolderConfig {
    private final FileJpaRepository fileJpaRepository;
    private final CodeRunner codeRunner;

    @Bean
    public FileFolderService fileFolderService() {
        return new DatabaseFileFolderService(fileJpaRepository, codeRunner);
    }
}
