package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;

public class JavaCodeRunningHandler implements CodeRunningHandler {

    @Override
    public String run(File file) {
        String code = file.getContent();
        return "";
    }
}
