package vn.snapbuck.sos.app;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import vn.snapbuck.sos.log.SBLog;
import vn.snapbuck.sos.utils.CommonUtils;
import vn.snapbuck.sos.utils.SBDeviceUtils;
import vn.snapbuck.sos.utils.SBLocationListener;
import vn.snapbuck.sos.utils.SBLocationUtils;
import vn.snapbuck.sos.utils.SharePreUtils;


/**
 * Created by sb4 on 4/20/15.
 */
public class SBApp extends Application implements
        com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static Context appContext;
    public static String PACKAGE_NAME;
    private static String userAgent = "";
    private static String accToken = "";
    private static String clientAppID;
    static double latitude;
    static double longitude;
    static Location location;
    static SBLocationListener locationListener;
    static GoogleApiClient googleApiClient;
    static LocationRequest locationRequest;

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

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationListener = new SBLocationListener();
        startLocationListener(500000);

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

    @Override
    public void onConnected(Bundle bundle) {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(5 * 1000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        googleApiClient.disconnect();
        if (_listenerLocation != null)
            _listenerLocation.onListenSucceeded(true);
        SBLog.Log(SBLog.LogType.Location, String.valueOf(latitude));
        SBLog.Log(SBLog.LogType.Location, String.valueOf(longitude));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        SBLog.Log(SBLog.LogType.Location, "Location Fail");
    }

    public static double getLatitude() {
//        startLocationListener(600000);
        return latitude;
    }

    public static double getLongitude() {
        //startLocationListener(60000);
        return longitude;
    }

    public static void setLatitude(double latitude) {
        SBApp.latitude = latitude;
    }

    public static void setLongitude(double longitude) {
        SBApp.longitude = longitude;
    }

    public static boolean startLocationListener(long MAX_AGE) {
        long timeNow = System.currentTimeMillis();
        long timeLastLocation = 0;
        boolean isGooglePlaySV = SBLocationUtils.isGooglePlayServiceAvalable(getSBAppContext());
        long timeCompare;
        if (location != null) {
            timeLastLocation = location.getTime();
            timeCompare = timeNow - timeLastLocation;
            if (timeCompare < MAX_AGE) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {
                if (isGooglePlaySV) {
                    try {
                        googleApiClient.connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                else {
//                    SBLocationListener.locationManager.requestLocationUpdates(
//                            LocationManager.NETWORK_PROVIDER, 2000l, 0f,
//                            locationListener);
//                }
            }
            return true;
        } else if (location == null) {
            if (isGooglePlaySV)
                googleApiClient.connect();
            else {
//                if (SBLocationListener.locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && SBLocationListener.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//                    SBLocationListener.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//                }
//                else if (SBLocationListener.locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && SBLocationListener.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    try {
//                        SBLocationListener.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                    } catch (Exception e) {
//
//                    }
//                }
            }
            return true;
        } else {
            return false;
        }

    }

    public static SBListenLocation _listenerLocation;

    public static void setLocationListener(SBListenLocation listener) {
        _listenerLocation = listener;
    }

    public interface SBListenLocation {
        boolean onListenSucceeded(boolean isLocationGet);
    }



}
