/**
 * 
 */
package mng.dload;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mng.dload.extra.GetSetAnnotedForm;
import mng.dload.extra.GetSetForm;

import org.junit.Before;
import org.junit.Test;

/**
 * @author mmangar
 * 
 */
public class GetSetAssist_Test {

    GetSetAssist gsa;

    @Before
    public void setUp() {
        gsa = new GetSetAssist();
        gsa.cp = AssistHelper.createClassPool();
    }

    @Test
    public void enhance_Doesnt_Extends_ActionAutoCrudImpl_Test() throws Exception {
        List<String> classes = new ArrayList<String>();
        classes.add("mng.dload.extra.GetSetForm");
        gsa.classes = classes;

        gsa.doEnhance();

        GetSetForm action = new GetSetForm();
        Method[] methods = action.getClass().getDeclaredMethods();

        assertEquals(0, methods.length);
    }

    @Test
    public void enhance_Extends_ActionAutoCrudImpl_Test() throws Exception {
        List<String> classes = new ArrayList<String>();
        classes.add("mng.dload.extra.GetSetAnnotedForm");
        gsa.classes = classes;

        gsa.doEnhance();

        GetSetAnnotedForm action = new GetSetAnnotedForm();
        Method[] methods = action.getClass().getDeclaredMethods();

        int fieldsLenght = action.getClass().getDeclaredFields().length;
        int methodsLenght = fieldsLenght * 2;

        assertEquals(methodsLenght, methods.length);

    }

}
