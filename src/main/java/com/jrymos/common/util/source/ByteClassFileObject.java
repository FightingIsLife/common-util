package com.jrymos.common.util.source;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * @version : 1
 * @auther: weijun.zou
 * @Date: 2018/12/18 23:38
 * @Description: 编译后的.class资源对象
 */
public final class ByteClassFileObject extends SimpleJavaFileObject{

    /**
     * 输出流：输出编译后的class字节码
     */
    private ByteArrayOutputStream outPutStream;

    public ByteClassFileObject(String className) {
        super(URI.create("Class:///" + className + Kind.CLASS.extension), Kind.CLASS);
    }

    @Override
    public OutputStream openOutputStream() {
        if (outPutStream == null){
            outPutStream = new ByteArrayOutputStream();
        }
        return outPutStream;
    }

    /**
     * 获取字节码
     */
    public byte[] bytes(){
        return outPutStream.toByteArray();
    }

}
