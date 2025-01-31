package goorm.fullstack.webide.execution.java;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavaMethodExecuter {

    public static String executeMainMethodFromLoadedClass(Class<?> loadedClass) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // 기존 System.out 저장
        System.setOut(new PrintStream(outputStream)); // System.out 리디렉션

        Method mainMethod = findMainMethodFromLoadedClass(loadedClass);
        executeMainMethod(mainMethod);

        System.setOut(originalOut);

        return outputStream.toString();
    }

    private static Method findMainMethodFromLoadedClass(Class<?> loadedClass) {
        Method mainMethod = null;
        try {
            mainMethod = loadedClass.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            System.out.println("No main method found in the class.");
        }
        return mainMethod;
    }

    private static void executeMainMethod(Method mainMethod) {
        // 메인 함수 실행
        if (mainMethod != null) {
            try {
                mainMethod.invoke(null, (Object) new String[]{});
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
