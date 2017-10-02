package com.example.erlangga.anakosapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateKosDetail extends AppCompatActivity {

    EditText mNamaKos;
    EditText mGenderKos;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_kos_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }
}
