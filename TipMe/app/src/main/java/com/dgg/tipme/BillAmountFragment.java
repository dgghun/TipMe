package com.dgg.tipme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BillAmountFragment extends Fragment {

    private View view;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        view = inflater.inflate(R.layout.fragment_bill_amount, container, false);

        return view;
    }
}
