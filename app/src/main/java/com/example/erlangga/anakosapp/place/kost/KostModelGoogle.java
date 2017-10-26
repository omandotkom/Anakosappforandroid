package com.example.erlangga.anakosapp.place.kost;

import android.location.Location;
import android.support.annotation.NonNull;

/**
 * Created by oman on 24/10/17.
 */

public class KostModelGoogle {
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Geometry getGeometri() {
        return geometri;
    }

    public void setGeometri(Geometry geometri) {
        this.geometri = geometri;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    //untuk daftar kost yang sumbernya dari google
    private String address;
    private Geometry geometri;
    private String icon;
    private String id;
    private String name;
    private String place_id;
    private String reference;
}


