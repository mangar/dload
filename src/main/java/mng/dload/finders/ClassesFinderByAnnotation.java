package mng.dload.finders;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

import javassist.CtClass;
import mng.dload.AssistHelper;

import org.apache.catalina.loader.WebappClassLoader;

public class ClassesFinderByAnnotation extends ClassesFinder {

    private static final Logger logger = Logger.getLogger(ClassesFinderByAnnotation.class.getName());

    public static ClassesFinderByAnnotation create() {
        ClassesFinderByAnnotation cf = new ClassesFinderByAnnotation();

        cf.classPool = AssistHelper.createClassPool();
        cf.classLoader = (WebappClassLoader) cf.classPool.getClass().getClassLoader();

        return cf;
    }

    // TEST
    public List<String> findClassesByAnnotationName(String annotationName) throws Exception {
        List<String> filesFound = new ArrayList<String>();
        if (this.classLoader != null) {
            URL[] urls = ((WebappClassLoader) this.classLoader).getURLs();
            for (URL url : urls) {
                File f = new File(url.getFile());
                List<String> extraFilesFound = this.findClassesAt(f, annotationName);
                filesFound.addAll(extraFilesFound);
            }
        }
        return filesFound;
    }

    // TEST
    public List<String> findClassesAt(File directory, String annotationName) throws Exception {
        List<String> filesFound = new ArrayList<String>();

        if (directory.getAbsolutePath().endsWith(".jar")) {

            // FIXME colocar a busca em classes dentro do jar
            // WARNING colocar a busca em classes dentro do jar
            // TODO colocar a busca em classes dentro do jar
            // JarFile jarFile = new JarFile(directory.getAbsolutePath());
            // filesFound.addAll(this.findClassesAt(jarFile, annotationName));

        } else {

            File[] files = directory.listFiles();
            if (files != null) {
                for (File currentFile : files) {

                    if (currentFile.isDirectory()) {
                        filesFound.addAll(this.findClassesAt(currentFile, annotationName));

                    } else {

                        CtClass cc = this.createCtClass(currentFile.getAbsolutePath());

                        if (AssistHelper.hasAnnotation(cc, annotationName)) {
                            filesFound.add(cc.getName());
                        }

                        this.detachCtClass(cc);

                    }
                }
            }
        }
        return filesFound;
    }

    // TEST
    public List<String> findClassesAt(JarFile jarFile, String annotationName) throws Exception {
        List<String> filesFound = new ArrayList<String>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry singleEntry = entries.nextElement();

            CtClass cc = this.createCtClass(singleEntry.getName());

            if (AssistHelper.hasAnnotation(cc, annotationName)) {
                filesFound.add(cc.getName());
            }

            this.detachCtClass(cc);

        }

        return filesFound;
    }

}
