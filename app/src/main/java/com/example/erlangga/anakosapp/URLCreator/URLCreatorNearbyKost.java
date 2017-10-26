package com.example.erlangga.anakosapp.URLCreator;

/**
 * Created by oman on 26/10/17.
 */

public class URLCreatorNearbyKost {
   private final String MAPS_API_KEY = "AIzaSyAyretCH6e8gbO9Q_UToRVgJPl-vLTHdqw";
   private double longitude,latitude;
    private int PROXIMITY_RADIUS = 10000;

    public URLCreatorNearbyKost(){}
    public URLCreatorNearbyKost(double latitude,double longitude,String nearbyPlace){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getURL(){
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        googlePlacesUrl.append("&key=" + MAPS_API_KEY);
        googlePlacesUrl.append("&location=" +latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&query=" + "kost");
    return googlePlacesUrl.toString();
    }

}

