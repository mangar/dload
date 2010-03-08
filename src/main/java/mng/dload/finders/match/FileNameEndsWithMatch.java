/**
 * 
 */
package mng.dload.finders.match;

/**
 * @author mmangar
 * 
 */
public class FileNameEndsWithMatch implements Match {

    String endsWithValue;

    public static FileNameEndsWithMatch create(String endsWithValue) {
        FileNameEndsWithMatch instance = new FileNameEndsWithMatch();
        instance.endsWithValue = endsWithValue;
        return instance;
    }

    private FileNameEndsWithMatch() {

    }

    public Boolean match(String data) {
        Boolean defaultReturn = Boolean.FALSE;
        if (data.endsWith(this.endsWithValue)) {
            defaultReturn = Boolean.TRUE;
        }
        return defaultReturn;
    }

}
