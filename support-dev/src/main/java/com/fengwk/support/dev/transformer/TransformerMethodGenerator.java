package com.fengwk.support.dev.transformer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.TypeUtils;

import com.fengwk.support.core.util.StringUtils;


/**
 * 
 * @author fengwk
 * @version V1.0
 * @since 2019-01-15 10:39
 */
public class TransformerMethodGenerator {

    private String methodSign;
    
    private Class<?> from;
    
    private Class<?> to;
    
    public TransformerMethodGenerator(String methodSign, Class<?> from, Class<?> to) {
        this.methodSign = methodSign;
        this.from = from;
        this.to = to;
    }
    
    public TransformerMethodGenerator(Class<?> from, Class<?> to) {
        this("transform", from, to);
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
        sb.append("public static ").append(getClassName(to)).append(' ').append(methodSign).append('(').append(getClassName(from)).append(' ').append(getName(from)).append(") {\n");
        sb.append("    if (").append(getName(from)).append(" == null) {\n");
        sb.append("        return null;\n");
        sb.append("    }\n");
        sb.append("    ").append(getClassName(to)).append(' ').append(getName(to)).append(" = new ").append(getClassName(to)).append("();\n");
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
                if (fromPd != null && fromPd.getReadMethod() != null && TypeUtils.isAssignable(targetWriteMethod.getGenericParameterTypes()[0], fromPd.getReadMethod().getGenericReturnType())) {
                    sb.append("    ").append(getName(to)).append('.').append(toPd.getWriteMethod().getName()).append('(').append(getName(from)).append('.').append(fromPd.getReadMethod().getName()).append("());\n");
                } else {
                    sb.append("    ").append(getName(to)).append('.').append(toPd.getWriteMethod().getName()).append("(null);\n");
                }
            }
        }
        sb.append("    return ").append(getName(to)).append(";\n");
        sb.append('}');
        return sb.toString();
    }
    
    private String getClassName(Class<?> clazz) {
        return clazz.getSimpleName();
    }
    
    private String getName(Class<?> clazz) {
        String className = getClassName(clazz);
        return StringUtils.lowerFirst(className);
    }
    
}
