package com.maakservicess.beingparents.app_monitor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maakservicess.beingparents.app_monitor.R;

/**
 * Created by SAI PC on 4/10/2016.
 */
public class Feeding_breast_Positions  extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feeding_breast_positions, container, false);

        return rootView;
    }
}
