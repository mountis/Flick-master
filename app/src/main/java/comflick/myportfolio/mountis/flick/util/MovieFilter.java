package comflick.myportfolio.mountis.flick.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



public class MovieFilter {
    public static final int POPULAR = 0;
    public static final int HIGH_RATED = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({POPULAR, HIGH_RATED})
    public @interface movieFilter{}

}
