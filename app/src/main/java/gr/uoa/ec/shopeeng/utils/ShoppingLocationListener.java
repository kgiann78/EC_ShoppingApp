package gr.uoa.ec.shopeeng.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class ShoppingLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
        Log.i(ShoppingLocationListener.class.getName(), "Location changed: Lat: " + loc.getLatitude() + " Lng: " + loc.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.i(ShoppingLocationListener.class.getName(), "onStatusChanged");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(ShoppingLocationListener.class.getName(), "onProviderDisabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(ShoppingLocationListener.class.getName(), "onProviderEnabled");
    }
}
