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
import mng.dload.finders.ClassesFinderByMatch;
import mng.dload.finders.match.GetSetAnnotationMatch;
import mng.dload.finders.match.Match;

import org.apache.catalina.loader.WebappClassLoader;
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
    Match match = GetSetAnnotationMatch.create();

    public static GetSetAssist create() throws Exception {
        GetSetAssist aa = new GetSetAssist();
        aa.classes = ClassesFinderByMatch.create(aa.match).findClassesAtCp();
        return aa;
    }

    public static GetSetAssist createWeb() throws Exception {
        GetSetAssist aa = new GetSetAssist();
        WebappClassLoader wcl = (WebappClassLoader) aa.cp.getClass().getClassLoader();
        aa.classes = ClassesFinderByMatch.create(aa.match).findClassesAt(wcl);
        return aa;
    }

    GetSetAssist() {
        cp = AssistHelper.createClassPool();
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
