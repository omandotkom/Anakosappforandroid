package com.example.erlangga.anakosapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;


public class MapsFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,
LocationListener{

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    final static int REQUEST_LOCATION = 199;
    private static final int MY_LOCATION_REQUEST_CODE = 99;
    public static String TAG = "ACTIVITY_MAPS";
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    MapView mMapView;
   Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    private GoogleMap googleMap;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
private    Marker KosLk, KosPrmpn;
    private double latitude=-1;
    private double longitude=-1;

    public MapsFragment(){

    }

    /*public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }*/
    public static MapsFragment newInstance(){
        MapsFragment fragment = new MapsFragment();
        return fragment;

    }

    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(getContext());
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
       try{LocationServices.getFusedLocationProviderClient(getContext()).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                   @Override
                   public void onLocationResult(LocationResult locationResult) {
                       // do work here
  //                     onLocationChanged(locationResult.getLastLocation());
                       //TODO: this is the main code for places
                       onLocationChanged(locationResult.getLastLocation());
               LocationServices.getFusedLocationProviderClient(getContext()).removeLocationUpdates(this);
                   }
               },
               Looper.myLooper());}catch(SecurityException se){
           Log.d(TAG,"Error : Security Exception, cause : " + se.getMessage());
       }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        buildGoogleApiClient();
    //startLocationUpdates();
        mMapView.onResume(); // needed to get the map to display immediately
Log.d(TAG,"View created...");
        // Construct a GeoDataClient.

        mGeoDataClient = Places.getGeoDataClient(getContext(), null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getContext(), null);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
googleMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
    @Override
    public void onPoiClick(PointOfInterest pointOfInterest) {
        Log.d(TAG,"POI CLICKED");

    }
});
                Log.d(TAG,"onMapReadyCallback");
             // double loc = mLastLocation.getLatitude();
             // Log.d(TAG,String.valueOf(loc));
               // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12.0f));
                //Initialize Google Play Services
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                       //TODO : Building google api client old, the new one is on onCreate method
                        //buildGoogleApiClient();
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }


                // For dropping a marker at a point on the Map
              //  LatLng sydney = new LatLng(-34, 151);
                //googleMap.addMarker(new MarkerOptions().position(sydney).title("Kos Laki-Laki").snippet("Marker Description"));

                //kos laki-laki dan perempuan
                LatLng pwt = new LatLng(-7.431391, 109.247833);
                googleMap.addMarker(new MarkerOptions().position(pwt).title("Warala kos"). snippet("Klik untuk lebih lanjut") .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                Log.d(TAG,"Marker added");
                LatLng magentaKos = new LatLng(-7.431391, 109.244454);
                googleMap.addMarker(new MarkerOptions().position(magentaKos).title("Magenta kos"). snippet("Klik untuk lebih lanjut") .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        if(marker.getTitle().equals("Warala kos")){
                            Intent i = new Intent(getActivity(), DetailKosActivity.class);
                            getActivity().startActivity(i);

                        }else if(marker.getTitle().equals("Magenta kos")){
                            Intent i = new Intent(getActivity(), DetailKosActivity.class);
                            getActivity().startActivity(i);

                        }
                    }
                });


                // For zooming automatically to the location of the marker
               // CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }


        });

        return rootView;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    /*private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");

       Log.d(TAG + " getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }*/

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG,"Succesfully Connected");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //TODO : here i make a comment
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Log.d(TAG,"Connected ->Permission granted");
startLocationUpdates();
        }else{
            Log.d(TAG,"Connected ->Permission not granted");
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
Log.d(TAG,"Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
Log.d(TAG,"Connection Failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //Place current location marker
        String newLoc = Double.toString(latitude) + "," + Double.toString(longitude);
        Log.d(TAG,"Location changed to " + newLoc);
        latitude= location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Your position");
        //Todo : Ubah jadi xml
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = googleMap.addMarker(markerOptions);

        //move map camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(25));
//TODO : Ini baru
        googleMap.clear();
       // String url = new URLCreatorNearbyKost(latitude,longitude,"kost").getURL();
        //Object[] DataTransfer = new Object[2];
        //DataTransfer[0] = googleMap;
        //DataTransfer[1] = url;
        //Log.d(TAG,"Places URL = " + url);

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(latitude,longitude,googleMap,getActivity().getApplicationContext());
        getNearbyPlacesData.showAll();
        //getNearbyPlacesData.execute(DataTransfer);
       // LocationServices.getFusedLocationProviderClient(getContext()).removeLocationUpdates(this);

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.d(TAG,"LocationUpdate's listener successfully removed");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }


}






