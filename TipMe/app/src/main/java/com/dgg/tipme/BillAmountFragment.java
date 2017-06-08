package com.dgg.tipme;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BillAmountFragment extends Fragment {

    private View view;
    private TextView TxtView_MainHeader;
    Typewriter writer_header;   //Custom class to animate textView text like a "type writer"


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        view = inflater.inflate(R.layout.fragment_bill_amount, container, false);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        setUpTextViewHeader();
    }


    public void setUpTextViewHeader() {
        TxtView_MainHeader = (TextView) view.findViewById(R.id.TextView_whatIsBillAmount);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DS-DIGIB.TTF");  //get custom typeface from assets
        TxtView_MainHeader.setTypeface(typeface);   //set header to custom typeFace
        TxtView_MainHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35); //change text size in SP

        //Add a character every few seconds
        writer_header = (Typewriter) TxtView_MainHeader;
        writer_header.setCharacterDelay(50);
        writer_header.animateText(getResources().getString(R.string.str_textview_what_is_bill_amount));
    }
}
