package mng.dload.finders;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.catalina.loader.WebappClassLoader;

public class ClassesFinderByName extends ClassesFinder {

    // TEST
    public static List<String> findClassesAt(WebappClassLoader wcl, String fileNamePattern) throws IOException {
        List<String> filesFound = new ArrayList<String>();
        if (wcl != null) {
            URL[] urls = wcl.getURLs();
            for (URL url : urls) {
                File f = new File(url.getFile());
                List<String> extraFilesFound = ClassesFinderByName.findClassesAt(f, fileNamePattern);
                filesFound.addAll(extraFilesFound);
            }
        }
        return filesFound;
    }

    public static List<String> findClassesAtCp(String fileNamePattern) throws IOException {
        List<String> filesFound = new ArrayList<String>();

        String classPath = System.getProperty("java.class.path");
        String[] pathElements = classPath.split(System.getProperty("path.separator"));

        for (String elem : pathElements) {
            File f = new File(elem);
            filesFound.addAll(ClassesFinderByName.findClassesAt(f, fileNamePattern));
        }

        return filesFound;
    }

    // TEST
    public static List<String> findClassesAt(File directory, String fileNamePattern) throws IOException {
        List<String> filesFound = new ArrayList<String>();

        if (directory.getAbsolutePath().endsWith(".jar")) {
            JarFile jarFile = new JarFile(directory.getAbsolutePath());
            filesFound.addAll(ClassesFinderByName.findClassesAt(jarFile, fileNamePattern));

        } else {

            File[] files = directory.listFiles();
            if (files != null) {
                for (File currentFile : files) {

                    if (currentFile.isDirectory()) {
                        filesFound.addAll(ClassesFinderByName.findClassesAt(currentFile, fileNamePattern));

                    } else {
                        if (currentFile.getAbsolutePath().endsWith(fileNamePattern)) {
                            String className = ClassesFinderByName.getCompleteClassFromPath(currentFile
                                    .getAbsolutePath());
                            filesFound.add(className);
                        }
                    }
                }
            }
        }
        return filesFound;
    }

    // TEST
    public static List<String> findClassesAt(JarFile jarFile, String fileNamePattern) throws IOException {
        List<String> filesFound = new ArrayList<String>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry singleEntry = entries.nextElement();
            if (singleEntry.getName().endsWith(fileNamePattern)) {
                String className = ClassesFinderByName.getCompleteClassFromPath(singleEntry.getName());
                filesFound.add(className);
            }
        }

        return filesFound;
    }

}
