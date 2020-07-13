package com.example;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static jdk.nashorn.internal.runtime.regexp.joni.constants.StackType.RETURN;

/**
 * 描述信息：
 *
 * @author xujiafeng
 * @date 2020/7/12
 */
public class TimeMethodVisitor extends MethodVisitor {
    private final String mClassName;
    private final String mMethodName;
    private final MethodVisitor methodVisitor;

    public TimeMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM5, methodVisitor);
        this.methodVisitor = methodVisitor;
        mClassName = className;
        mMethodName = methodName;
    }

    @Override
    public void visitCode() {

        mv.visitLdcInsn(mMethodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/asmlifecycledemo/TimeCache", "setStartTime", "(Ljava/lang/String;J)V", false);

        super.visitCode();

        mv.visitLdcInsn(mMethodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/asmlifecycledemo/TimeCache", "setEndTime", "(Ljava/lang/String;J)V", false);

    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }
}
