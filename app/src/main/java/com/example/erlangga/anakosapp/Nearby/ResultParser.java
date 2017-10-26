package com.example.erlangga.anakosapp.Nearby;

import android.util.Log;

import com.example.erlangga.anakosapp.place.kost.Geometry;
import com.example.erlangga.anakosapp.place.kost.KostModelGoogle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oman on 24/10/17.
 */

public class ResultParser {
    private final String RESULT = "results";
    private final String FORMATTED_ADDRESS = "formatted_address";
    private final String GEOMETRY = "geometry";
    private final String LOCATION = "location";
    private final String LATITUDE = "lat";
    private final String LONGTITUDE = "lng";
    private final String ICON = "icon";
    private final String ID = "id";
    private final String NAME = "name";
    private final String PLACE_ID = "place_id";

    private JSONObject jsonObject;
    private ArrayList<KostModelGoogle> kostModelGoogleArrayList;

    public ResultParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        kostModelGoogleArrayList = new ArrayList<KostModelGoogle>();
    }

    public void parse() {
        try {
            if (jsonObject.length() != 0) {
                JSONArray jsonArray = jsonObject.getJSONArray(RESULT);
                for (int i = 0; i <= jsonArray.length() - 1; i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    //ambil alamat
                    KostModelGoogle kostModelGoogle = new KostModelGoogle();
                    kostModelGoogle.setAddress(jsonObject.getString(FORMATTED_ADDRESS));
                    //ambil json location
                    JSONObject jsonLoc = jsonObject.getJSONObject(GEOMETRY).getJSONObject(LOCATION);
                    //ambil data lat dan lng
                    kostModelGoogle.setGeometri(new Geometry(jsonLoc.getDouble(LATITUDE), jsonLoc.getDouble(LONGTITUDE)));
                    kostModelGoogle.setIcon(jsonObject.getString(ICON));
                    kostModelGoogle.setId(jsonObject.getString(ID));
                    kostModelGoogle.setName(jsonObject.getString(NAME));
                    kostModelGoogle.setPlace_id(jsonObject.getString(PLACE_ID));
            kostModelGoogleArrayList.add(kostModelGoogle);
                }
            }
        } catch (JSONException jse) {
            Log.d("Voll", "JSON Exception " + jse.getMessage());
        }
        getKostModelGoogleList();
    }
public ArrayList<KostModelGoogle> getKostModelGoogleList() {
        return this.kostModelGoogleArrayList;
}
}
