package com.example;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author xuyu
 * @date 2020/5/13
 */
public class LifeCycleMethodVisitor extends MethodVisitor {

    private final String mClassName;
    private final String mMethodName;

    public LifeCycleMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM5, methodVisitor);
        mClassName = className;
        mMethodName = methodName;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("visitCode");

        mv.visitLdcInsn("TAG");
        mv.visitLdcInsn(mClassName + " " + mMethodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }
}
