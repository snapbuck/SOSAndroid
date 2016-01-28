package vn.snapbuck.sos.log;

import android.util.Log;


/**
 * Created by sb4 on 4/21/15.
 */
public class SBLog {
    static final String APP_NAME = "SOS";

    public static void Log(LogType type, String message) {
        if (isLogTypeEnable(type)) {
            if (type == LogType.Exception) {
                Log.e(APP_NAME, ">>>>>>>>>>>>>>" + type.name() + "\n" + message);
            } else {
                Log.d(APP_NAME, ">>>>>>>>>>>>>>" + type.name() + "\n" + message);
            }
        }
    }

    private static boolean isLogTypeEnable(LogType type) {
        return true;
    }

    public enum LogType {
        General,
        Exception,
        Database,
        Network,
        Camera,
        Location,
    }

}
