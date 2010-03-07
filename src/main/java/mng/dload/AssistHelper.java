/**
 * 
 */
package mng.dload;

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

}
