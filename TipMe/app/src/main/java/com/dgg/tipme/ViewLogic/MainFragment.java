package com.dgg.tipme.ViewLogic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgg.tipme.R;

/**
 * Created by David_Garcia on 5/11/2017.
 */

public class MainFragment extends Fragment {

    private View view;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){
        view = inflater.inflate(R.layout.fragment_main, container, false);


        return view;
    }
}
