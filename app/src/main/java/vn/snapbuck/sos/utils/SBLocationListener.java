package vn.snapbuck.sos.utils;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import vn.snapbuck.sos.app.SBApp;


public class SBLocationListener implements LocationListener {
    public static LocationManager locationManager;
	@Override
	public void onLocationChanged(Location loc) {
        double latitude = loc.getLatitude();
        double longitude = loc.getLongitude();
        SBApp.setLatitude(latitude);
        SBApp.setLongitude(longitude);
        locationManager.removeUpdates(this);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
