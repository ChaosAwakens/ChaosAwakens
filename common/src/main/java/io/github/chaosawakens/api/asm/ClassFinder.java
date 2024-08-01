package io.github.chaosawakens.api.asm;

import io.github.chaosawakens.CAConstants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public final class ClassFinder {

    /**
     * Uses {@code org.objectweb.asm} in order to discover (and load) classes annotated with the specified annotation type class, or an empty list if there aren't any/the {@code annotationType} passed in isn't an annotation.
     *
     * @param annotationType The annotation type class.
     * @return A list of classes annotated with the specified annotation type.
     */
    public static List<Class<?>> discoverAnnotatedClasses(Class<?> annotationType) {
        if (!annotationType.isAnnotation()) {
            CAConstants.LOGGER.warn("Attempted to discover/load annotated classes from a non-annotation type: {}", annotationType);
            return List.of();
        }

        List<Class<?>> annotatedClasses = new ObjectArrayList<>();
        List<File> classFiles = new ObjectArrayList<>(); // All existing class files from the classpath
        String classpath = System.getProperty("java.class.path");

        for (String curClassPath : classpath.split(File.pathSeparator)) {
            File curFile = new File(curClassPath);

            if (curFile.isDirectory()) {
                try (Stream<Path> allExistingPaths = Files.walk(curFile.toPath(), FileVisitOption.FOLLOW_LINKS)) {
                    allExistingPaths.filter(Files::isRegularFile)
                            .filter(curPath -> curPath.toString().endsWith(".class"))
                            .forEach(curVerifiedPath -> classFiles.add(curVerifiedPath.toFile()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (File classFile : classFiles) {
            try (InputStream classInputStream = new FileInputStream(classFile)) {
                ClassReader classReader = new ClassReader(classInputStream);

                classReader.accept(new ClassVisitor(Opcodes.ASM9) {
                    private String className;

                    @Override
                    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                        this.className = name.replace('/', '.');
                    }

                    @Override
                    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                        if (Type.getDescriptor(annotationType).equals(descriptor)) {
                            try {
                                annotatedClasses.add(Class.forName(className));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                        return super.visitAnnotation(descriptor, visible);
                    }
                }, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return annotatedClasses;
    }
}
