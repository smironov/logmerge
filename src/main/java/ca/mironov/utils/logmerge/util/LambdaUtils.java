package ca.mironov.utils.logmerge.util;

import java.util.concurrent.Callable;

public class LambdaUtils {

    public static <V> V rethrow(Callable<V> callable) {
        try {
            return callable.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
