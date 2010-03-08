/**
 * 
 */
package mng.dload;

import java.util.logging.Logger;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import mng.dload.annotations.GetSet;

/**
 * @author mmangar
 * 
 */
public class AssistHelper {

    private static final Logger logger = Logger.getLogger(AssistHelper.class.getName());

    public static ClassPool createClassPool() {
        ClassPool cp = ClassPool.getDefault();
        cp.insertClassPath(new ClassClassPath(AssistHelper.class));
        return cp;
    }

    public static Boolean isMethodImplemented(CtClass cc, String methodName) {
        Boolean is = Boolean.FALSE;
        CtMethod[] theDeclaredMethods = cc.getDeclaredMethods();
        for (CtMethod me : theDeclaredMethods) {
            if (me.getName().equals(methodName)) {
                is = Boolean.TRUE;
                break;
            }
        }
        return is;
    }

    public static Boolean extendsSuperClass(CtClass cc, String superClass) throws Exception {
        Boolean extended = Boolean.FALSE;
        if (cc != null) {
            CtClass superC = cc.getSuperclass();
            String cn = superC.getName();
            extended = cn.endsWith(superClass);
        }
        return extended;
    }

    public static Boolean hasAnnotation(CtClass cc, String annotationName) throws Exception {
        Boolean annotated = Boolean.FALSE;
        if (cc != null) {
            Object[] annotations = cc.getAnnotations();
            if (annotations != null) {
                for (Object a : annotations) {
                    // FIXME instanceof GetSet ? or annotationName ?
                    if (a instanceof GetSet) {
                        annotated = Boolean.TRUE;
                        break;
                    }
                }
            }
        }
        return annotated;
    }

    public static String getCompleteClassFromPath(String absolutePath) {
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

    public static CtClass createCtClass(String absolutePath) {
        CtClass cc = null;

        if (AssistHelper.isFileConsidered(absolutePath)) {
            try {
                String className = AssistHelper.getCompleteClassFromPath(absolutePath);
                cc = AssistHelper.createClassPool().get(className);
            } catch (Exception e) {
                logger.severe(e.toString());
            }
        }
        return cc;
    }

    public static CtClass detachCtClass(CtClass cc) {
        if (cc != null) {
            cc.detach();
        }
        return cc;
    }

    // FIXME find a better way...
    static Boolean isFileConsidered(String absolutePath) {
        Boolean considerIt = Boolean.FALSE;

        if (absolutePath.contains("mng")) {
            considerIt = Boolean.TRUE;
        } else if (absolutePath.contains("com/mg")) {
            considerIt = Boolean.TRUE;
        }

        if (absolutePath.endsWith(".")) {
            considerIt = Boolean.FALSE;
        }

        return considerIt;
    }

}
