package goorm.fullstack.webide.execution;

import goorm.fullstack.webide.execution.java.JavaClassFileLoader;
import goorm.fullstack.webide.execution.java.JavaFileCompiler;
import goorm.fullstack.webide.execution.java.JavaFileHandler;
import goorm.fullstack.webide.execution.java.JavaMethodExecuter;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1. 코드를 자바 파일로 만든다. 2. 자바 파일을 컴파일 한다. 3. 컴파일로 생성된 클래스 코드에서 함수를 찾아 실행한다. 4. Stream을 이용하여 출력된 결과를
 * 응답으로 전달한다.
 */
public class JavaCodeRunningHandler implements CodeRunningHandler {

    private final JavaFileCompiler javaFileCompiler;
    private String className;
    private goorm.fullstack.webide.domain.File codeFile;
    private File javaFile;
    private Class<?> loadedClass;
    private String executionResult;
    private final Pattern classPattern;

    public JavaCodeRunningHandler() {
        this.javaFileCompiler = new JavaFileCompiler();
        this.classPattern = Pattern.compile("public\\s+class\\s+(\\w+)");
    }

    @Override
    public String run(goorm.fullstack.webide.domain.File codeFile) {
        setup(codeFile);
        saveJavaFile();
        compileJavaFile();
        loadClassFromClassFile();
        executeMainMethodFromLoadedClass();
        cleanupJavaFileAndClassFile();

        return executionResult;
    }

    private void setup(goorm.fullstack.webide.domain.File codeFile) {
        className = extractClassName(codeFile.getContent());
        this.codeFile = codeFile;
    }

    private String extractClassName(String code) {
        Matcher matcher = classPattern.matcher(code);
        if (matcher.find()) {
            return matcher.group(1); // 클래스 이름 반환
        }
        throw new RuntimeException("Class name not found in the provided code.");
    }

    private void saveJavaFile() {
        javaFile = JavaFileHandler.save(codeFile, className);
    }

    private void compileJavaFile() {
        javaFileCompiler.compile(javaFile);
    }

    private void loadClassFromClassFile() {
        loadedClass = JavaClassFileLoader.load(className);
    }

    private void executeMainMethodFromLoadedClass() {
        executionResult = JavaMethodExecuter.executeMainMethodFromLoadedClass(loadedClass);
    }

    private void cleanupJavaFileAndClassFile() {
        JavaFileHandler.cleanupWithClassName(className);
    }

}
