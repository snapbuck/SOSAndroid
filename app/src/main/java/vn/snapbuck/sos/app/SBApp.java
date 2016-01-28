package vn.snapbuck.sos.app;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;

import vn.snapbuck.sos.log.SBLog;
import vn.snapbuck.sos.utils.CommonUtils;
import vn.snapbuck.sos.utils.SBDeviceUtils;
import vn.snapbuck.sos.utils.SharePreUtils;


/**
 * Created by sb4 on 4/20/15.
 */
public class SBApp extends Application {

    private static Context appContext;
    public static String PACKAGE_NAME;
    private static String userAgent = "";
    private static String accToken = "";
    private static String clientAppID;

    public static Context getSBAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        SBApp.appContext = appContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();
        PACKAGE_NAME = appContext.getPackageName();

        // Setup handler for uncaught exceptions.//
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                handleUncaughtException(thread, ex);
            }
        });

    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically
        SBLog.Log(SBLog.LogType.Exception,e.getLocalizedMessage());
        SystemClock.sleep(1000);
        System.exit(1); // kill off the crashed app
    }

    public static String getUserAgent() {
        if (userAgent == null || userAgent.equals(""))
            userAgent = SBDeviceUtils.getUserAgent(appContext);

        return userAgent;
    }

    public static String getAccToken() {
        if (accToken == null || accToken.equals(""))
            accToken = CommonUtils.getAccessTokenSercu(appContext);

        return accToken;
    }

    public static String getClientAppID() {
        if (clientAppID == null || clientAppID == "")
            clientAppID = CommonUtils.getClientAppID(appContext);
        return clientAppID;
    }

    public static String getUserId() {
        return SharePreUtils.getStringPreferences(getSBAppContext(), "userID");
    }

}
