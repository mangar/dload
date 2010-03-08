package mng.dload.finders;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import mng.dload.AssistHelper;
import mng.dload.finders.match.Match;

import org.apache.catalina.loader.WebappClassLoader;

public class ClassesFinderByMatch {

    Match match;

    public static ClassesFinderByMatch create(Match match) {
        ClassesFinderByMatch instance = new ClassesFinderByMatch();
        instance.match = match;
        return instance;
    }

    private ClassesFinderByMatch() {
    }

    // TEST
    public List<String> findClassesAt(WebappClassLoader wcl) throws Exception {
        List<String> filesFound = new ArrayList<String>();
        if (wcl != null) {
            URL[] urls = wcl.getURLs();
            for (URL url : urls) {
                File f = new File(url.getFile());
                List<String> extraFilesFound = this.findClassesAt(f);
                filesFound.addAll(extraFilesFound);
            }
        }
        return filesFound;
    }

    public List<String> findClassesAtCp() throws Exception {
        List<String> filesFound = new ArrayList<String>();

        String classPath = System.getProperty("java.class.path");
        String[] pathElements = classPath.split(System.getProperty("path.separator"));

        for (String elem : pathElements) {
            File f = new File(elem);
            filesFound.addAll(this.findClassesAt(f));
        }

        return filesFound;
    }

    // TEST
    public List<String> findClassesAt(File directory) throws Exception {
        List<String> filesFound = new ArrayList<String>();

        if (directory.getAbsolutePath().endsWith(".jar")) {
            JarFile jarFile = new JarFile(directory.getAbsolutePath());
            filesFound.addAll(this.findClassesAt(jarFile));

        } else {

            File[] files = directory.listFiles();
            if (files != null) {
                for (File currentFile : files) {

                    if (currentFile.isDirectory()) {
                        filesFound.addAll(this.findClassesAt(currentFile));

                    } else {
                        if (this.match.match(currentFile.getAbsolutePath())) {
                            String className = AssistHelper.getCompleteClassFromPath(currentFile.getAbsolutePath());
                            filesFound.add(className);
                        }
                    }
                }
            }
        }
        return filesFound;
    }

    // TEST
    public List<String> findClassesAt(JarFile jarFile) throws IOException {
        List<String> filesFound = new ArrayList<String>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry singleEntry = entries.nextElement();
            if (this.match.match(singleEntry.getName())) {
                String className = AssistHelper.getCompleteClassFromPath(singleEntry.getName());
                filesFound.add(className);
            }
        }
        return filesFound;
    }

}
