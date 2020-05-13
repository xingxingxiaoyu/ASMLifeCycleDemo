package com.example

import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import groovy.io.FileType
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter


public class LifeCycleTransform extends Transform {

    @Override
    String getName() {
        return "LifeCycleTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        Collection<TransformInput> inputs = transformInvocation.inputs

        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        inputs.each { input ->
                input.jarInputs.each { JarInput jarInput ->
                    File file = jarInput.file
                    System.out.println("find jar input: " + file.name)
                    def dest = outputProvider.getContentLocation(jarInput.name,
                            jarInput.contentTypes,
                            jarInput.scopes, Format.JAR)
                    FileUtils.copyFile(file, dest)
                }

                input.directoryInputs.each { directoryInput ->
                    File dir = directoryInput.file
                    if (dir) {
                        dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { file ->
                            System.out.println("find class:" + file.name)

                            def reader = new ClassReader(file.bytes)
                            def writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
                            def classVisitor = new LifecycleClassVisitor(writer)
                            reader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                            def bytes = writer.toByteArray()
                            def fos = new FileOutputStream(file.path)
                            fos.write(bytes)
                            fos.flush()
                            fos.close()
                        }
                    }
                    def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                    FileUtils.copyDirectory(directoryInput.file, dest)
                }


        }

    }
}