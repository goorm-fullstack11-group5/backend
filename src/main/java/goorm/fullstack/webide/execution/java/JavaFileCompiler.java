package goorm.fullstack.webide.execution.java;

import java.io.File;
import java.util.List;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaFileCompiler {
    private final JavaCompiler javaCompiler;
    private final StandardJavaFileManager fileManager;

    public JavaFileCompiler() {
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
        this.fileManager = javaCompiler.getStandardFileManager(null, null, null);
    }

    public void compile(File javaFile) {
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(
            List.of(javaFile));

        CompilationTask compilationTask = javaCompiler.getTask(null, fileManager, null, null, null,
            compilationUnits);

        boolean success = compilationTask.call();
        if (!success) {
            throw new RuntimeException("Compile failed.");
        }
    }
}
