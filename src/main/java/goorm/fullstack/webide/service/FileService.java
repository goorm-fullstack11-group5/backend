package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.dto.*;

/**
 * 파일을 데이터베이스에 저장하는 방법을 먼저 구현하고, 추후 파일 시스템이나 다른 방법을 사용해 구현하는 경우를 생각하여 인터페이스로 추상화함.
 */
public interface FileService {

    File createFile(FileRequestDto fileRequestDto);

    void deleteFile(int id);

    File readFileContent(int id);

    File updateFile(int id, FileUpdateRequestDto fileUpdateRequestDto);

    String runFile(int id);
}
