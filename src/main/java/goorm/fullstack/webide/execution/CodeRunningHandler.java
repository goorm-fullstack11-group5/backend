package goorm.fullstack.webide.execution;

import goorm.fullstack.webide.domain.File;

public interface CodeRunningHandler {
    String run(File file);
}
