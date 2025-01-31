package goorm.fullstack.webide.execution.java;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class JavaClassFileLoader {

    public static Class<?> load(String className) {
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
        return dynamicClass;
    }
}
