package com.jrymos.common.util.source;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/18 23:58
 * @Description: 字节码资源管理器
 */
public class BytesJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    public final Location BYTE_LOCATION = StandardLocation.CLASS_OUTPUT;

    /**
     * 字节码资源存储
     */
    private final Map<String, ByteClassFileObject> byteClassFileObjectMap = new ConcurrentHashMap<>();

    /**
     * 类加载器
     */
    private final ClassLoader classLoader = new ClassLoader() {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            ByteClassFileObject byteClassFileObject = byteClassFileObjectMap.get(name);
            if (byteClassFileObject != null) {
                byte[] bytes = byteClassFileObject.bytes();
                return defineClass(name, bytes, 0, bytes.length);
            }
            return super.findClass(name);
        }
    };

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     */
    public BytesJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
        if (BYTE_LOCATION.equals(location)) {
            return byteClassFileObjectMap.get(className);
        }
        return super.getJavaFileForInput(location, className, kind);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        if (BYTE_LOCATION.equals(location)) {
            return byteClassFileObjectMap.compute(className,
                    (k, byteClassFileObject) -> byteClassFileObject == null ? new ByteClassFileObject(className) : byteClassFileObject);
        }
        return super.getJavaFileForOutput(location, className, kind, sibling);
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        if (BYTE_LOCATION.equals(location)) {
            return classLoader;
        }
        return super.getClassLoader(location);
    }
}
