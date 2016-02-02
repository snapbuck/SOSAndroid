package vn.snapbuck.sos.utils;

import android.content.Context;
import android.location.LocationManager;
import android.provider.Settings;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


/**
 * Created by sb4 on 4/22/15.
 */
public class SBLocationUtils {

    public static boolean checkGPS(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        boolean isGPS = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return isGPS;
    }

    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        return !Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
    }

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371; // kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c * 1000;
        return dist;
    }


    public static boolean isGooglePlayServiceAvalable(Context context) {
        boolean isGoogleServiceAvailable = false;
        int resp = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resp == ConnectionResult.SUCCESS) {
            isGoogleServiceAvailable = true;
        }
        return isGoogleServiceAvailable;
    }


}
