/**
 * 
 */
package mng.dload.finders.match;

import java.util.logging.Logger;

import javassist.CtClass;
import mng.dload.AssistHelper;
import mng.dload.GetSetAssist;

/**
 * @author mmangar
 * 
 */
public class GetSetAnnotationMatch implements Match {

    private static final Logger logger = Logger.getLogger(GetSetAnnotationMatch.class.getName());

    public static GetSetAnnotationMatch create() {
        GetSetAnnotationMatch instance = new GetSetAnnotationMatch();
        return instance;
    }

    private GetSetAnnotationMatch() {

    }

    public Boolean match(String data) {
        Boolean defaultReturn = Boolean.FALSE;
        CtClass cc = AssistHelper.createCtClass(data);
        try {
            if (AssistHelper.hasAnnotation(cc, GetSetAssist.GETSET_ANNOTATION)) {
                defaultReturn = Boolean.TRUE;
            }
        } catch (Exception e) {
            logger.severe("Problem to match at GetSetAnnotationMatch(" + data + ") : " + e.toString());
        }
        AssistHelper.detachCtClass(cc);
        return defaultReturn;
    }

}
