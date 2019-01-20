package com.jrymos.common.util.source;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/18 23:32
 * @Description: java源代码资源对象
 */
public final class StringJavaFileObject extends SimpleJavaFileObject {

    /**
     * 源码
     */
    private String source;

    public StringJavaFileObject(String source, String name) {
        super(URI.create("String:///" + name + Kind.SOURCE.extension), Kind.SOURCE);
        this.source = source;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return source;
    }
}
