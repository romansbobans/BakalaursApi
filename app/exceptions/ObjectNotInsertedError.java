package exceptions;

import com.mongodb.WriteResult;

/**
 * Created by TAHKICT on 28/05/14.
 */
public class ObjectNotInsertedError extends DBExceptions {
    public ObjectNotInsertedError(WriteResult result) {
    }
}
