package com.fengwk.support.core.filesystem;

import java.net.URL;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

/**
 * 
 * @author fengwk
 */
public abstract class Entry {
    
    protected final ClassLoader cl;
    
    protected volatile URL url;
    
    protected volatile List<Entry> children;
    
    protected Entry(ClassLoader cl, URL url) {
        this.cl = cl;
        this.url = url;
    }
    
    public abstract boolean isDir();
    
    public abstract boolean isFile();

    public boolean hasChildren() {
        return CollectionUtils.isNotEmpty(children());
    }
    
    public abstract List<Entry> children();
    
    public URL url() {
        return url;
    }

}
