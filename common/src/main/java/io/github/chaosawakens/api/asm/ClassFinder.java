package io.github.chaosawakens.api.asm;

import io.github.chaosawakens.CAConstants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {

    /**
     * Indexes and returns a {@link List} of all classes in a given package. If the package contains any {@linkplain JarFile JarFiles}, they will be scanned as well using a {@link JarURLConnection}.
     *
     * @param packageName The target package to scan. For instance, {@code "io.github.chaosawakens.platform.services"}.
     *
     * @return A {@link List} of all classes in the target package, including ones matching the specified {@code packageName} in any {@linkplain JarFile JarFiles} present within said package (if any).
     */
    public static List<String> findClasses(String packageName) {
        List<String> classNames = new ObjectArrayList<>();
        String path = packageName.replace('.', '/');

        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();

                if (resource.getProtocol().equals("file")) classNames.addAll(findClassesInDirectory(new File(resource.getFile()), "src/main/java/" + packageName));
                else if (resource.getProtocol().equals("jar")) {
                    JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                    classNames.addAll(findClassesInJar(jarURLConnection.getJarFile(), path));
                }
            }
        } catch (IOException e) {
            CAConstants.LOGGER.warn("Failed to find classes in package: " + packageName);
            e.printStackTrace();
        }
        return classNames;
    }

    /**
     * Indexes and returns a {@link List} of all classes in a given directory & package.
     *
     * @param directory The full target directory to scan. For instance, {@code new File("src/main/java/io/github/chaosawakens")}.
     * @param packageName The logical grouping/representation of the target {@code directory}. For instance, {@code "io.github.chaosawakens"}.
     *
     * @return A {@link List} of all classes in the given directory & package, or an empty {@link List} if no classes are found/the target directory does not exist.
     */
    public static List<String> findClassesInDirectory(File directory, String packageName) {
        List<String> classNames = new ObjectArrayList<>();

        if (!directory.exists()) {
            CAConstants.LOGGER.warn("Attempted to find classes in non-existent directory: " + directory);
            return classNames;
        }

        File[] dirFiles = directory.listFiles();

        if (dirFiles != null) {
            for (File file : dirFiles) {
                if (file.isDirectory()) classNames.addAll(findClassesInDirectory(file, packageName + "." + file.getName()));
                else if (file.getName().endsWith(".class")) classNames.add(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)); // Remove .class extension
            }
        }

        return classNames;
    }

    /**
     * Sifts through a {@link JarFile} and returns a {@link List} of all classes (full logical packages) in a given base package.
     *
     * @param jarFile The target {@link JarFile}.
     * @param path The base package to search. For instance, {@code "io/github/chaosawakens"}.
     *
     * @return A {@link List} of all classes in the given base package, or an empty {@link List} if no classes are found.
     */
    public static List<String> findClassesInJar(JarFile jarFile, String path) {
        List<String> classNames = new ObjectArrayList<>();
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();

            if (name.startsWith(path) && name.endsWith(".class") && !entry.isDirectory()) {
                String className = name.replace('/', '.').substring(0, name.length() - 6);
                classNames.add(className);
            }
        }
        return classNames;
    }
}
