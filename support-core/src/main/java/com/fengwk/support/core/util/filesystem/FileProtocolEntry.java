package com.fengwk.support.core.util.filesystem;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author fengwk
 */
class FileProtocolEntry extends Entry {

    protected volatile File file;
    
    FileProtocolEntry(ClassLoader cl, URL url) {
        super(cl, url);
    }

    @Override
    public boolean isDir() {
        return file().isDirectory();
    }

    @Override
    public boolean isFile() {
        return !isDir();
    }
    
    public List<Entry> children() {
        if (isFile()) {
            throw new UnsupportedOperationException("File entry have no children.");
        }
        if (children == null) {
            synchronized (this) {
                if (children == null) {
                    children = new ArrayList<>();
                    File[] files = file.listFiles();
                    for (File childFile : files) {
                        URL childUrl = null;
                        try {
                            childUrl = childFile.toURI().toURL();
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                        FileProtocolEntry child = new FileProtocolEntry(cl, childUrl);
                        child.file = childFile;
                        children.add(child);
                    }
                }
            }
        }
        return children;
    }

    protected File file() {
        if (file == null) {
            synchronized (this) {
                if (file == null) {
                    file = new File(url.getFile());
                }
            }
        }
        return file;
    }
    
}
