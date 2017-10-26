package com.example.erlangga.anakosapp.Nearby;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONObject;

/**
 * Created by oman on 24/10/17.
 */

public class GetNearbyKost {
    private String googlePlacesData;
    private GoogleMap mMap;
    private String url;
    private Context context;
    private JSONObject jsonObject;
    public GetNearbyKost(String url,Context context){
        this.mMap = mMap;
        this.url= url;
        this.context = context;
    }

    public void getDataFromURL(final ResponseCallback responseCallback){
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, this.url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ResultParser parser = new ResultParser(response);
                        parser.parse();
                    responseCallback.onGetNearbyKostDataLoaded(parser.getKostModelGoogleList());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley",  "VolleyError=>" + error.getMessage());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
