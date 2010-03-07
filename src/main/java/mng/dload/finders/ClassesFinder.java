/**
 * 
 */
package mng.dload.finders;

import java.util.logging.Logger;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * @author mmangar
 * 
 */
public class ClassesFinder {

    private static final Logger logger = Logger.getLogger(ClassesFinder.class.getName());

    protected ClassPool classPool;
    protected ClassLoader classLoader;

    static String getCompleteClassFromPath(String absolutePath) {
        String newClassPath = absolutePath;

        int indexOfClasses = newClassPath.indexOf("classes");
        if (indexOfClasses >= 0) {
            newClassPath = newClassPath.substring(indexOfClasses + 7);
        }

        if (newClassPath.startsWith("/")) {
            newClassPath = newClassPath.substring(1);
        }

        newClassPath = newClassPath.replace("/", ".");
        newClassPath = newClassPath.replace(".class", "");
        return newClassPath;
    }

    CtClass createCtClass(String absolutePath) {
        CtClass cc = null;

        // TODO tratar, nao considerar .properties, .xml

        try {
            String className = ClassesFinderByAnnotation.getCompleteClassFromPath(absolutePath);
            cc = classPool.get(className);
        } catch (Exception e) {
            logger.severe(e.toString());
        }
        return cc;
    }

    CtClass detachCtClass(CtClass cc) {
        if (cc != null) {
            cc.detach();
        }
        return cc;
    }

}
