package com.fengwk.support.core.util.filesystem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 
 * @author fengwk
 */
public class ClassPaths {

    static final String PROTOCOL_FILE = "file";
    static final String PROTOCOL_JAR = "jar";
    
    private final ClassLoader cl;

    public ClassPaths() {
        this(ClassLoader.getSystemClassLoader());
    }
    
    public ClassPaths(ClassLoader cl) {
        this.cl = cl;
    }
    
    /**
     * 
     * @param path 当前上下文类加载器的加载路径{@link ClassLoader#getResources(String)}
     * @return
     */
    public List<Entry> of(String path) {
        if (path == null) {
            throw new NullPointerException("Path cannot be null.");
        }
        Enumeration<URL> urls;
        try {
            urls = cl.getResources(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Entry> entries = new ArrayList<>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();
            if (PROTOCOL_FILE.equalsIgnoreCase(protocol)) {
                entries.add(new FileProtocolEntry(cl, url));
            } else if (PROTOCOL_JAR.equalsIgnoreCase(protocol)) {
                entries.add(new JarProtocolEntry(cl, url));
            } else {
                throw new IllegalStateException("Not supported protocol:" + protocol);
            }
        }
        return entries;
    }
    
}
