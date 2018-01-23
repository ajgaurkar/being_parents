package com.maakservicess.beingparents.app_monitor.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maakservicess.beingparents.app_monitor.R;

/**
 * Created by Amey on 26-02-2017.
 */
public class InfantMassage_Instruction extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.infant_massage_by_instruction, container, false);
        return rootView;
    }
}
