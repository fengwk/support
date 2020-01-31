package com.fengwk.support.dev.transformer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fengwk.support.core.util.StringUtils;

/**
 * 
 * @author fengwk
 * @version V1.0
 * @since 2019-01-15 12:31
 */
public class TransformerClassGenerator {

    // toPO
    public static final int SIGN_MODE_1 = 1;
    
    // bOToPO
    public static final int SIGN_MODE_2 = 2;
    
    private int signMode = SIGN_MODE_1;
    
    private String packagePath;
    
    private PojoClass po;
    
    private PojoClass bo;
    
    private PojoClass vo;
    
    private PojoClass dto;
    
    public TransformerClassGenerator(String packagePath, int signMode, Class<?> poClass, Class<?> boClass, Class<?> voClass, Class<?> dtoClass) {
        this.signMode = signMode;
        this.packagePath = packagePath;
        this.po = new PojoClass(poClass, 2);
        this.bo = new PojoClass(boClass, 2);
        this.vo = new PojoClass(voClass, 2);
        this.dto = new PojoClass(dtoClass, 3);
    }
    
    public void generateFile(String path) throws IOException {
        if (packagePath == null) {
            throw new IOException("包路径不能为空");
        }
        packagePath = packagePath.replace(".", "/");
        path = path + File.separator + getClassName() + ".java";
        File file = new File(path);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (file.exists()) {
            throw new IOException(path + "已存在");
        }
        FileOutputStream output = new FileOutputStream(file);
        ByteArrayInputStream input = new ByteArrayInputStream(generate().getBytes(StandardCharsets.UTF_8));
        org.apache.commons.io.IOUtils.copy(input, output);
        output.close();
    }
    
    public String generate() {
        StringBuilder sb = new StringBuilder();
        if (packagePath != null) {
            sb.append("package ").append(packagePath).append(";\n\n");
        }
        sb.append(getImport(po));
        sb.append(getImport(bo));
        sb.append(getImport(vo));
        sb.append(getImport(dto));
        sb.append('\n');
        sb.append("public class ").append(getClassName()).append(" {\n\n");
        sb.append("    private ").append(getClassName()).append("() {}");
        sb.append(getMethod(po, bo));
        sb.append(getMethod(bo, vo));
        sb.append(getMethod(bo, dto));
        sb.append("\n\n");
        sb.append('}');
        return sb.toString();
    }
    
    private String getClassName() {
        String className = "Transformer";
        if (!po.isNull()) {
            className = po.realName + className;
        } else if (!bo.isNull()) {
            className = bo.realName + className;
        } else if (!vo.isNull()) {
            className = vo.realName + className;
        } else if (!dto.isNull()) {
            className = dto.realName + className;
        }
        return className;
    }
    
    private String getImport(PojoClass pojoClass) {
        StringBuilder sb = new StringBuilder();
        if (!pojoClass.isNull()) {
            sb.append("import ").append(pojoClass.classPath).append(";\n");
        }
        return sb.toString();
    }
    
    private String getMethod(PojoClass from, PojoClass to) {
        StringBuilder sb = new StringBuilder();
        if (!from.isNull() && !to.isNull()) {
            sb.append("\n\n");
            sb.append(getMethod0(from, to));
            sb.append("\n\n");
            sb.append(getMethod0(to, from));
        }
        return sb.toString();
    }
    
    private String getMethod0(PojoClass from, PojoClass to) {
        TransformerMethodGenerator transformerMethodGenerator = new TransformerMethodGenerator(getMethodSign(from, to), from.clazz, to.clazz);
        String method = transformerMethodGenerator.generate();
        String[] lines = method.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; i ++) {
            sb.append("    ").append(lines[i]);
            if (i < lines.length - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }
    
    private String getMethodSign(PojoClass from, PojoClass to) {
        if (signMode == SIGN_MODE_1) {
            return "to" + to.suffix;
        }
        if (signMode == SIGN_MODE_2) {
            return StringUtils.lowerFirst(from.suffix + "To" + to.suffix);
        }
        return null;
    }
    
}
