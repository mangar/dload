package mng.dload;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AssisstHelper_getCompleteClassFromPath_Test {

    @Test
    public void getCompleteClassFromPath_Test() {
        String expected = "mng.dload.extra.ClassAnnotated";

        String path1 = "/Users/som/user/projects/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/web/WEB-INF/classes/mng/dload/extra/ClassAnnotated.class";
        String newPath1 = AssistHelper.getCompleteClassFromPath(path1);
        assertEquals(expected, newPath1);

        String path2 = "/mng/dload/extra/ClassAnnotated.class";
        String newPath2 = AssistHelper.getCompleteClassFromPath(path2);
        assertEquals(expected, newPath2);

        String path3 = "mng/dload/extra/ClassAnnotated.class";
        String newPath3 = AssistHelper.getCompleteClassFromPath(path3);
        assertEquals(expected, newPath3);
    }

}
