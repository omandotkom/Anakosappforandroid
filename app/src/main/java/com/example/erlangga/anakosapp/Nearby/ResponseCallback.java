package com.example.erlangga.anakosapp.Nearby;

import com.example.erlangga.anakosapp.place.kost.KostModelGoogle;

import java.util.ArrayList;

/**
 * Created by oman on 26/10/17.
 */

public interface ResponseCallback {
    void onGetNearbyKostDataLoaded(ArrayList<KostModelGoogle> kostModelGoogleArrayList);
}
