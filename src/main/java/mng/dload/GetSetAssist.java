/**
 * 
 */
package mng.dload;

import java.util.List;
import java.util.logging.Logger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import mng.dload.finders.ClassesFinderByAnnotation;

import org.apache.commons.lang.StringUtils;

/**
 * @author mmangar
 * 
 */
public class GetSetAssist {

    private static final Logger logger = Logger.getLogger(GetSetAssist.class.getName());

    public static final String GETSET_ANNOTATION = "GetSet";

    ClassPool cp;
    List<String> classes;

    public static GetSetAssist create() throws Exception {
        GetSetAssist aa = new GetSetAssist();
        aa.cp = AssistHelper.createClassPool();

        // TODO padronizar a chamada, com ou sem ClassLoader
        // TODO sincronizar entre o GetSet e o Action
        // ClassLoader cl = (WebappClassLoader) aa.cp.getClass().getClassLoader();
        aa.classes = ClassesFinderByAnnotation.create().findClassesByAnnotationName(GetSetAssist.GETSET_ANNOTATION);

        return aa;
    }

    GetSetAssist() {

    }

    public void doEnhance() throws Exception {
        if (classes != null) {
            for (String clazz : classes) {
                this.overwriteClass(clazz);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void overwriteClass(String clazz) throws Exception {
        CtClass cc = cp.get(clazz);

        Boolean hasGetSetAnnotation = this.hasGetSetAnnotation(cc);

        if (hasGetSetAnnotation) {
            logger.info("Enhancing Bean: " + clazz);
            cc = this.makeGettersAndSetters(cc);
            // TODO trocar por algo diferente .write, etc....
            // WARNING trocar por algo diferente .write, etc....
            // FIXME trocar por algo diferente .write, etc....
            Class c = cc.toClass();
        }

        cc.detach();
    }

    Boolean hasGetSetAnnotation(CtClass cc) throws Exception {
        Boolean ext = Boolean.FALSE;
        ext = AssistHelper.hasAnnotation(cc, GetSetAssist.GETSET_ANNOTATION);
        return ext;
    }

    public CtClass makeGettersAndSetters(CtClass cc) throws Exception {

        CtField[] ctFields = cc.getFields();

        if (ctFields != null) {
            for (CtField field : ctFields) {
                cc = this.makeGet(cc, field);
                cc = this.makeSet(cc, field);
            }
        }
        return cc;
    }

    public CtClass makeGet(CtClass cc, CtField field) throws Exception {
        if (cc != null && field != null) {
            String fieldName = field.getName();
            String typeClassName = field.getType().getName();

            String capFieldName = StringUtils.capitalize(fieldName);
            String getFieldName = "get" + capFieldName;

            if (!AssistHelper.isMethodImplemented(cc, getFieldName)) {
                String methodBody = "public " + typeClassName + " " + getFieldName + "() { return this." + fieldName
                        + "; }";
                CtMethod getDomain = CtNewMethod.make(methodBody, cc);
                cc.addMethod(getDomain);
            }
        }
        return cc;
    }

    public CtClass makeSet(CtClass cc, CtField field) throws Exception {
        if (cc != null && field != null) {
            String fieldName = field.getName();
            String typeClassName = field.getType().getName();

            String capFieldName = StringUtils.capitalize(fieldName);
            String setFieldName = "set" + capFieldName;

            if (!AssistHelper.isMethodImplemented(cc, setFieldName)) {
                String methodBody = "public void " + setFieldName + "(" + typeClassName + " " + fieldName
                        + ") { return this." + fieldName + " = " + fieldName + "; }";
                CtMethod getDomain = CtNewMethod.make(methodBody, cc);
                cc.addMethod(getDomain);
            }
        }
        return cc;
    }

}
