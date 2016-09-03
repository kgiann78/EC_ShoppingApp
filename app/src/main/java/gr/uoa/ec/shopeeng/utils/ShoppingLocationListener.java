package gr.uoa.ec.shopeeng.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class ShoppingLocationListener implements LocationListener {
    private Location location;

    @Override
    public void onLocationChanged(Location loc) {
        location = loc;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
