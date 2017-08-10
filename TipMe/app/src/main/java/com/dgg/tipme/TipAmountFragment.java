package com.dgg.tipme;

import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class TipAmountFragment extends Fragment {

    private View view;
    private TextView mTxtView_MainHeader;
    private Typewriter mWriter_header;
    private ImageView mImgView_badBot;
    private ImageView mImgView_goodBot;
    private ImageView mImgView_greatBot;


    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        view = inflater.inflate(R.layout.fragment_tip_amount, container, false);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        setUpTextViewHeader();
    }


    /**
     * setUpTextviewHeader()
     */
    public void setUpTextViewHeader() {
        mTxtView_MainHeader = (TextView) view.findViewById(R.id.TextView_howWasService);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DS-DIGIB.TTF");  //get custom typeface from assets
        mTxtView_MainHeader.setTypeface(typeface);   //set header to custom typeFace
//        mTxtView_MainHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35); //change text size in SP

        //Animate text header
        mWriter_header = (Typewriter) mTxtView_MainHeader;
        mWriter_header.setCharacterDelay(50);
        mWriter_header.animateText(getResources().getString(R.string.str_textview_how_was_srvc), false);
    }   //END of setupTextViewHeader()

}
