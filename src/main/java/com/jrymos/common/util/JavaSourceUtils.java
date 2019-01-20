package com.jrymos.common.util;

import com.jrymos.common.util.source.BytesJavaFileManager;
import com.jrymos.common.util.source.StringJavaFileObject;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.nio.charset.Charset;
import java.util.Collections;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/31 12:05
 * @Description: 动态编译java源代码，并实例化
 */
public class JavaSourceUtils {

    private static JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private static DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
    private static BytesJavaFileManager manager = new BytesJavaFileManager(compiler.getStandardFileManager(diagnostics, null, Charset.forName("UTF-8")));

    public static <T> T newInstance(String packageName, String className, String javaSource) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        JavaCompiler.CompilationTask compilationTask = compiler
                .getTask(null, manager, diagnostics, null, null, Collections.singleton(new StringJavaFileObject(javaSource, className)));
        compilationTask.call();
        ClassLoader classLoader = manager.getClassLoader(manager.BYTE_LOCATION);
        return (T) classLoader.loadClass(packageName + "." + className).newInstance();
    }
}
