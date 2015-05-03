package org.osgl.oms;

import org.osgl.oms.util.ClassNames;
import org.osgl.util.E;
import org.osgl.util.IO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TestBootstrapClassLoader extends BootstrapClassLoader {

    public TestBootstrapClassLoader(ClassLoader cl) {
        super(cl);
    }

    protected byte[] tryLoadResource(String name) {
        if (!name.startsWith("org.osgl.oms.")) return null;
        String fn = ClassNames.classNameToClassFileName(name, true);
        URL url = findResource(fn.substring(1));
        if (null == url) return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IO.copy(url.openStream(), baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw E.ioException(e);
        }
    }

    @Override
    protected URL findResource(String name) {
        return Thread.currentThread().getContextClassLoader().getResource(name);
    }
}