package io.github.chaosawakens.api.services;

import com.google.common.reflect.ClassPath;
import io.github.chaosawakens.api.asm.ClassFinder;
import io.github.chaosawakens.api.loader.ModLoader;
import io.github.chaosawakens.api.platform.services.IPlatformHelper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public ModLoader getPlatform() {
        return ModLoader.FABRIC;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public List<Class<?>> discoverAnnotatedClasses(Class<? extends Annotation> annotationTypeClazz) {
        try {
            return ClassPath.from(FabricLoader.getInstance().getClass().getClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(curClassInfo -> {
                        boolean foundValidAnnotation = false;
                        ClassReader reader = null;

                        try {
                            reader = new ClassReader(curClassInfo.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9) {
                            private String className;

                            @Override
                            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                                this.className = name.replace('/', '.');
                            }

                            @Override
                            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                                return super.visitAnnotation(desc, visible);
                            }
                        };
                        reader.accept(visitor, 0);
                        return foundValidAnnotation;
                    })
                    .map(ClassPath.ClassInfo::getName)
                    .map(ClassFinder::forName)
                    .collect(Collectors.toCollection(ObjectArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
            return ObjectArrayList.of();
        }
    }
}
