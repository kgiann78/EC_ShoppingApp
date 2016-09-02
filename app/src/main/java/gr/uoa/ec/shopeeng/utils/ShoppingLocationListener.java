package gr.uoa.ec.shopeeng.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import gr.uoa.ec.shopeeng.listeners.LocationUpdateListener;

public class ShoppingLocationListener implements LocationListener {
    LocationUpdateListener locationUpdateListener;

    public ShoppingLocationListener() {
    }

    public void onLocationUpdated(LocationUpdateListener locationUpdateListener) {
        this.locationUpdateListener = locationUpdateListener;
    }

    @Override
    public void onLocationChanged(Location loc) {
        if (locationUpdateListener != null)
            locationUpdateListener.onLocationUpdated(loc);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }
}
