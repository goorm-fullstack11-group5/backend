package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;

public interface CodeRunningHandler {
    String run(String code);
}
