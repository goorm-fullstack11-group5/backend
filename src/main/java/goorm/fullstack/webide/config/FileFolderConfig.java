package goorm.fullstack.webide.config;

import goorm.fullstack.webide.repository.FileJpaRepository;
import goorm.fullstack.webide.service.DatabaseFileFolderService;
import goorm.fullstack.webide.service.FileFolderService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FileFolderConfig {
    private final FileJpaRepository fileJpaRepository;

    @Bean
    public FileFolderService fileFolderService() {
        return new DatabaseFileFolderService(fileJpaRepository);
    }
}
