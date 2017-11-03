package com.example.erlangga.anakosapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Profile extends Fragment implements View.OnClickListener{


    public static final String TAG = "PROFILE";


    public Profile() {
        // Required empty public constructor
    }


    public static Profile newInstance(){
        return new Profile();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Button mUpdateKos = (Button) v.findViewById(R.id.updateKos);

        mUpdateKos.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.updateKos:
                //TODO : Ini nanti di enable lagi
                //Intent i = new Intent(getActivity(), UpdateKosDetail.class);
                //getActivity().startActivity(i);
                //break;

        }
    }



}
