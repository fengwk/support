package com.fengwk.support.dev.transformer;

import com.fengwk.support.core.util.StringUtils;

// XxxPO
public class PojoClass {

    protected Class<?> clazz;

    // **.xxxPO
    protected String classPath;
    
    // XxxPO
    protected String className;
    
    // xxxPO
    protected String name;
    
    // PO
    protected String suffix;
    
    // Xxx
    protected String realName;
    
    protected PojoClass(Class<?> clazz, int sufixLen) {
        if (clazz != null) {
            this.clazz = clazz;
            this.classPath = clazz.getName();
            this.className = clazz.getSimpleName();
            this.name = StringUtils.lowerFirst(className);
            this.suffix = this.name.substring(this.name.length() - sufixLen);
            this.realName = this.className.substring(0, this.className.length() - sufixLen);
        }
    }
    
    protected boolean isNull() {
        return clazz == null;
    }
    
}
