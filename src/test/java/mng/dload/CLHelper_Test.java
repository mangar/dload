package mng.dload;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author mmangar
 * 
 */
public class CLHelper_Test {

    @Test
    @Ignore
    public void findClassesInClassDir_Test() throws IOException {
        List<String> files = null; // ClassesFinderByName.findClassesAtCp("Form.class");

        System.out.println("--------------------------------------------------------------");
        System.out.println(files);
        System.out.println("--------------------------------------------------------------");

        assertTrue(files.size() > 0);
    }

}
