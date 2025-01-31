package goorm.fullstack.webide.config;

import goorm.fullstack.webide.repository.FileJpaRepository;
import goorm.fullstack.webide.execution.CodeRunningHandlerMapper;
import goorm.fullstack.webide.service.DatabaseFileSystemService;
import goorm.fullstack.webide.service.FileService;
import goorm.fullstack.webide.service.FileSystemService;
import goorm.fullstack.webide.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FileSystemConfig {
    private final FileJpaRepository fileJpaRepository;
    private final CodeRunningHandlerMapper codeRunningHandlerMapper;

    @Bean
    public FileSystemService fileSystemService() {
        return new DatabaseFileSystemService(fileJpaRepository, codeRunningHandlerMapper);
    }

    @Bean
    public FileService fileService() {
        return fileSystemService();
    }

    @Bean
    public FolderService folderService() {
        return fileSystemService();
    }
}
