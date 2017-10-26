package com.example.erlangga.anakosapp.place.kost;

import android.location.Location;
import android.support.annotation.NonNull;

/**
 * Created by oman on 25/10/17.
 */

public class Geometry{

    Location location = new Location("com.example.erlangga.anakosapp.place.kost");
    public Geometry(@NonNull double latitude, @NonNull double longitude){
       this.location.setLatitude( latitude);
       this.location.setLongitude(longitude);
    }
    public Location getLocation() {
     return location;
     }
     public String toString(){
        String returnVal = String.valueOf(this.location.getLatitude()) + "," + String.valueOf(this.location.getLongitude());
        return returnVal;
     }

    public void setLocation(Double latitude, Double longtitude) {
        this.location.setLatitude(latitude);
        this.location.setLongitude(longtitude);
    }
    //public void setLocation(Location location){
      //  this.location = location;
    //}
}