package com.dgg.tipme.ViewLogic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.dgg.tipme.R;

/**
 * Created by David_Garcia on 5/11/2017.
 */

public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);   //Inflate from this xml layout file

        //Explicit calls to fragments. Also, using support library
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment  = fm.findFragmentById(R.id.fragment_container); //id of container in XML file

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
