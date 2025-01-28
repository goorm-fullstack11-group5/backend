package goorm.fullstack.webide.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 1. 코드를 자바 파일로 만든다.
 * 2. 자바 파일을 컴파일 한다.
 * 3. 컴파일로 생성된 클래스 코드에서 함수를 찾아 실행한다.
 * 4. Stream을 이용하여 출력된 결과를 응답으로 전달한다.
 */
public class JavaCodeRunningHandler implements CodeRunningHandler {

    private final JavaCompiler javaCompiler;
    private final StandardJavaFileManager fileManager;

    public JavaCodeRunningHandler() {
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
        this.fileManager = javaCompiler.getStandardFileManager(null, null, null);
    }

    @Override
    public String run(String code) {
        String className = extractClassName(code);
        String fileName = className + ".java";

        File sourceFile = new File(fileName);
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write(code);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(
            List.of(sourceFile));

        CompilationTask compilationTask = javaCompiler.getTask(null, fileManager, null, null, null,
            compilationUnits);

        boolean success = compilationTask.call();
        if (!success) {
            throw new RuntimeException("Compile failed.");
        }

        URLClassLoader classLoader = null;
        try {
            classLoader = URLClassLoader.newInstance(new URL[]{new File(".").toURI().toURL()});
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Class<?> dynamicClass = null;
        try {
            dynamicClass = Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // 기존 System.out 저장
        System.setOut(new PrintStream(outputStream)); // System.out 리디렉션

        // 실행할 메인 함수가 있는지 확인
        Method mainMethod = null;
        try {
            mainMethod = dynamicClass.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            System.out.println("No main method found in the class: " + className);
        }

        // 메인 함수 실행
        if (mainMethod != null) {
            try {
                mainMethod.invoke(null, (Object) new String[]{});
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        System.setOut(originalOut);

        // 캡처된 결과를 문자열로 저장
        String executionResult = outputStream.toString();

        // 6. 정리
        sourceFile.delete();
        new File(className + ".class").delete();

        return executionResult;
    }

    private String extractClassName(String code) {
        Pattern classPattern = Pattern.compile("public\\s+class\\s+(\\w+)");
        Matcher matcher = classPattern.matcher(code);
        if (matcher.find()) {
            return matcher.group(1); // 클래스 이름 반환
        }
        throw new RuntimeException("Class name not found in the provided code.");
    }
}
