package com.fengwk.support.core.filesystem;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 
 * @author fengwk
 */
class JarProtocolEntry extends Entry {
    
    protected volatile String path;
    
    protected volatile boolean isInit = false;
    
    protected volatile Boolean isDir;
    
    JarProtocolEntry(ClassLoader cl, URL url) {
        super(cl, url);
    }
    
    @Override
    public boolean isDir() {
        autoLazyInit();
        return isDir;
    }

    @Override
    public boolean isFile() {
        return !isDir();
    }
    
    @Override
    public List<Entry> children() {
        if (isFile()) {
            throw new UnsupportedOperationException("File entry have no children.");
        }
        return children;
    }
    
    public URL url() {
        autoLazyInit();
        return url;
    }
    
    private void autoLazyInit() {
        if (!isInit) {
            synchronized (this) {
                if (!isInit) {
                    lazyInit();
                }
            }
        }
    }
    
    private void lazyInit() {
        JarURLConnection conn;
        try {
            conn = (JarURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (JarFile jarFile = conn.getJarFile()) {
            path = conn.getEntryName();
            String rootDirPath = null;
            if (!path.endsWith("/")) {
                rootDirPath = path + "/";
            }
            List<String> paths = new ArrayList<>();
            isDir = false;
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                String childPath = jarEntry.getName();
                if (childPath.equals(path)) {
                    isDir = true;
                } else if (rootDirPath != null && childPath.equals(rootDirPath)) {
                    isDir = true;
                    path += rootDirPath;
                    url = new URL(url.toString() + "/");
                }
                paths.add(childPath);
            }
            if (isDir) {
                Map<String, List<Entry>> childrenMap = new HashMap<>();
                fillChildrenMap(childrenMap, paths, path);
                List<Entry> children = childrenMap.get(path);
                this.children = children;
                fillChildren(childrenMap, children);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void fillChildrenMap(Map<String, List<Entry>> childrenMap, List<String> paths, String rootPath) {
        List<Entry> children = new ArrayList<>();
        childrenMap.put(rootPath, children);
        for (String path : paths) {
            if (isChild(rootPath, path)) {
                JarProtocolEntry child = new JarProtocolEntry(cl, cl.getResource(path));
                child.path = path;
                child.isDir = path.endsWith("/");
                children.add(child);
                if (child.isDir) {
                    fillChildrenMap(childrenMap, paths, path);
                }
            }
        }
    }
    
    private boolean isChild(String rootPath, String childPath) {
        int rootLen = rootPath.length();
        int childLen = childPath.length();
        if (childLen <= rootLen || !childPath.startsWith(rootPath)) {
            return false;
        }
        int idx = childPath.indexOf("/", rootLen);
        return idx == -1 || idx == childPath.length() - 1;
    }
    
    private void fillChildren(Map<String, List<Entry>> childrenMap, List<Entry> children) {
        for (Entry child : children) {
            JarProtocolEntry jpeChild = (JarProtocolEntry) child;
            if (jpeChild.isDir) {
                jpeChild.children = childrenMap.get(((JarProtocolEntry) child).path);
                if (jpeChild.children == null) {
                    jpeChild.children = Collections.emptyList();
                } else {
                    fillChildren(childrenMap, jpeChild.children);
                }
            }
            jpeChild.isInit = true;
        }
    }

}
