package com.example.erlangga.anakosapp;

import android.content.Context;
import android.util.Log;

import com.example.erlangga.anakosapp.Nearby.GetNearbyKost;
import com.example.erlangga.anakosapp.Nearby.ResponseCallback;
import com.example.erlangga.anakosapp.URLCreator.URLCreatorNearbyKost;
import com.example.erlangga.anakosapp.place.kost.KostModelGoogle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GetNearbyPlacesData{
private final String TAG = "NBD";
    private double latitude, longitude;
    private String googlePlacesData;
    //google places data ganti sama
    private GoogleMap mMap;
    private Context context;
public GetNearbyPlacesData(){}
public GetNearbyPlacesData(double latitude, double longitude,GoogleMap mMap,Context context){
   this. mMap = (GoogleMap) mMap;
   this.context = context;
   this.latitude = latitude;
   this.longitude = longitude;
}

public void showAll(){
    String url = new URLCreatorNearbyKost(latitude,longitude,"kost").getURL();
    Log.d(TAG,url);
    GetNearbyKost getNearbyKost = new GetNearbyKost(url,this.context);
    getNearbyKost.getDataFromURL(new ResponseCallback() {
        @Override
        public void onGetNearbyKostDataLoaded(ArrayList<KostModelGoogle> kostModelGoogleArrayList) {

            for (int i = 0; i < kostModelGoogleArrayList.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                double lat = kostModelGoogleArrayList.get(i).getGeometri().getLocation().getLatitude();
                double lng =  kostModelGoogleArrayList.get(i).getGeometri().getLocation().getLongitude();
                String placeName = kostModelGoogleArrayList.get(i).getName();
                String address = kostModelGoogleArrayList.get(i).getAddress();
                LatLng latLng = new LatLng(lat, lng);
                markerOptions.position(latLng);
                markerOptions.title(placeName);
                markerOptions.snippet(address);
                markerOptions.flat(true);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                mMap.addMarker(markerOptions);
                 //move map camera

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                //Log.d(TAG,"Marker added, Name " + kostModelGoogleArrayList.get(i).getName());
            }
        };
        })    ;
}


/*
    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
            Log.d("GooglePlacesReadTask",googlePlacesData);
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }
/*
    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList =  dataParser.parse(result);
        ShowNearbyPlaces(nearbyPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }*/

    /*private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute","Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }*/
}