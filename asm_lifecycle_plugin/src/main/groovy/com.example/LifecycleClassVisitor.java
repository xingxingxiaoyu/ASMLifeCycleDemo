package com.example;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author xuyu
 * @date 2020/5/13
 */
public class LifecycleClassVisitor extends ClassVisitor {

    private String mSuperName;
    private String mName;

    public LifecycleClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mSuperName = superName;
        mName = name;
        System.out.println("visit:" + name + " superName " + superName);

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("visitMethod:" + name);
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (mSuperName.equals("androidx/appcompat/app/AppCompatActivity")) {
            if (name.equals("onCreate")) {
                return new TimeMethodVisitor(methodVisitor, mName, name);
            }
        }
        return methodVisitor;
    }
}
