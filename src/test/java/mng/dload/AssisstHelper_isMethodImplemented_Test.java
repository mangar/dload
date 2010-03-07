package mng.dload;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javassist.ClassPool;
import javassist.CtClass;

import org.junit.Before;
import org.junit.Test;

public class AssisstHelper_isMethodImplemented_Test {

    ClassPool cp;
    CtClass ccWithMethods;
    CtClass ccWithOutMethods;

    @Before
    public void setUp() throws Exception {
        cp = AssistHelper.createClassPool();
        ccWithMethods = cp.get("mng.dload.extra.ClassWithMethods");
        ccWithOutMethods = cp.get("mng.dload.extra.ClassWithoutMethods");
    }

    @Test
    public void isMethodImplemented_True_Test() {
        Boolean isImplemented = AssistHelper.isMethodImplemented(ccWithMethods, "withMethod");
        assertTrue(isImplemented);
    }

    @Test
    public void isMethodImplemented_False_Test() {
        Boolean isImplemented = AssistHelper.isMethodImplemented(ccWithOutMethods, "withMethod");
        assertFalse(isImplemented);
    }

}
