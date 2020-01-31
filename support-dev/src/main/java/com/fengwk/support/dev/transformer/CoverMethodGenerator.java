package com.fengwk.support.dev.transformer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.TypeUtils;

/**
 * 
 * @author fengwk
 * @version V1.0
 * @since 2019-01-15 10:39
 */
public class CoverMethodGenerator {

    private String methodSign;
    
    private Class<?> from;
    
    private Class<?> to;
    
    private int mode;// 1-selected;2-cover;3-smart;
    
    public CoverMethodGenerator(String methodSign, Class<?> from, Class<?> to, int mode) {
        this.methodSign = methodSign;
        this.from = from;
        this.to = to;
        this.mode = mode;
    }
    
    public CoverMethodGenerator(Class<?> from, Class<?> to, int mode) {
        this("cover", from, to, mode);
    }
    
    public CoverMethodGenerator(Class<?> from, Class<?> to) {
        this(from, to, 1);
    }
    
    public String generate() {
        return generate(null);
    }
    
    public String generate(String[] excludes) {
        List<String> excludes0 = null;
        if (excludes != null) {
            excludes0 = Arrays.asList(excludes);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("public static void ").append(methodSign).append('(').append(getClassName(to)).append(' ').append("targetBO").append(", ").append(getClassName(from)).append(' ').append("srcBO").append(") {\n");
        sb.append("    if (").append("targetBO == null || srcBO == null) {\n");
        sb.append("        return;\n");
        sb.append("    }\n");
        PropertyDescriptor[] toPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(to);
        Arrays.sort(toPds, (x1, x2) -> x1.getName().compareTo(x2.getName()));
        for (PropertyDescriptor toPd : toPds) {
            Method targetReadMethod = toPd.getReadMethod();
            Method targetWriteMethod = toPd.getWriteMethod();
            if (targetReadMethod != null && targetWriteMethod != null) {
                if (excludes0 != null && excludes0.contains(toPd.getName())) {
                    continue;
                }
                PropertyDescriptor fromPd = org.springframework.beans.BeanUtils.getPropertyDescriptor(from, toPd.getName());
                if (mode == 1) {
                    sb.append("    ").append("if(").append("targetBO").append('.').append(toPd.getReadMethod().getName()).append("() == null) {").append("\n    ");
                }
                if (fromPd != null && fromPd.getReadMethod() != null && TypeUtils.isAssignable(targetWriteMethod.getGenericParameterTypes()[0], fromPd.getReadMethod().getGenericReturnType())) {
                    sb.append("    ").append("targetBO").append('.').append(toPd.getWriteMethod().getName()).append('(').append("srcBO").append('.').append(fromPd.getReadMethod().getName()).append("());\n");
                } else {
                    sb.append("    ").append("targetBO").append('.').append(toPd.getWriteMethod().getName()).append("(null);\n");
                }
                if (mode == 1) {
                	sb.append("    }").append('\n');
                }
            }
        }
        sb.append('}');
        return sb.toString();
    }
    
    private String getClassName(Class<?> clazz) {
        return clazz.getSimpleName();
    }
    
}
