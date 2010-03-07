package mng.dload;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javassist.ClassPool;
import javassist.CtClass;

import org.junit.Before;
import org.junit.Test;

public class AssisstHelper_extendsSuperClass_Test {

    ClassPool cp;
    CtClass ccAnnotated;
    CtClass ccNotAnnotated;

    @Before
    public void setUp() throws Exception {
        cp = AssistHelper.createClassPool();
        ccAnnotated = cp.get("mng.dload.extra.ClassAnnotated");
        ccNotAnnotated = cp.get("mng.dload.extra.ClassNotAnnotated");
    }

    @Test
    public void extendsSuperClass_True_Test() throws Exception {
        Boolean isImplemented = AssistHelper.hasAnnotation(ccAnnotated, GetSetAssist.GETSET_ANNOTATION);
        assertTrue(isImplemented);
    }

    @Test
    public void extendsSuperClass_False_Test() throws Exception {
        Boolean isImplemented = AssistHelper.hasAnnotation(ccNotAnnotated, GetSetAssist.GETSET_ANNOTATION);
        assertFalse(isImplemented);
    }

}
