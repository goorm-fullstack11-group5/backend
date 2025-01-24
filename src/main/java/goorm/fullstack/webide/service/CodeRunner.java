package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CodeRunner {
    private final Map<String, CodeRunningHandler> codeRunningHandlerMap = new HashMap<>();

    public CodeRunner() {
        // todo: 확장자에 따라서 CodeRunningHandler 등록
        this.codeRunningHandlerMap.put("java", new JavaCodeRunningHandler());
    }

    public String run(File file) {
        // todo : 파일의 확장자 가져오기
        String fileExtension = null;
        for (Map.Entry<String, CodeRunningHandler> entry : codeRunningHandlerMap.entrySet()) {
            if (entry.getKey().equals(fileExtension)) {
                return entry.getValue().run(file);
            }
        }
        return "";
    }
}
