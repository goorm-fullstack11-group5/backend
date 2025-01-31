package goorm.fullstack.webide.execution.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JavaFileHandler {
    public static File save(goorm.fullstack.webide.domain.File codeFile, String className) {
        String fileName = className + ".java";
        String code = codeFile.getContent();

        File sourceFile = new File(fileName);
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write(code);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sourceFile;
    }

    public static void cleanupWithClassName(String className) {
        new File(className + ".java").delete();
        new File(className + ".class").delete();
    }
}
