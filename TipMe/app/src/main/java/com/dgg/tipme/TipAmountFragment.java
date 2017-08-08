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
    private Typewriter writer_header;   //Custom class to animate textView text like a "type writer"
    private ImageView mImgView_badHead;
    private ImageView mImgView_goodHead;
    private ImageView mImgView_greatHead;
    private AnimationDrawable mAnim_badHead;
    private AnimationDrawable mAnim_goodHead;
    private AnimationDrawable mAnim_greatHead;
    private TextView mTxtView_MainHeader;
    private Typewriter mWriter_header;
    private ImageView mImgView_badBot;
    private ImageView mImgView_goodBot;
    private ImageView mImgView_greatBot;
    private AnimationDrawable animateDrawable_badBot;
    private AnimationDrawable animateDrawable_goodBot;
    private AnimationDrawable animateDrawable_greatBot;


    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        view = inflater.inflate(R.layout.fragment_tip_amount, container, false);

        setUpTipBotImageView();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        setUpTextViewHeader();
        startAnimations();
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


    /** setUpTipBotImageView()
     *
     */
    public void setUpTipBotImageView(){
        mImgView_badBot = (ImageView)view.findViewById(R.id.imageView_badTipHead);
        mImgView_goodBot = (ImageView)view.findViewById(R.id.imageView_goodTipHead);
        mImgView_greatBot = (ImageView)view.findViewById(R.id.imageView_greatTipHead);

        mImgView_badBot.setImageAlpha(0); // Hide the original image so you can see the animated images play..
        mImgView_goodBot.setImageAlpha(0); // Hide the original image so you can see the animated images play..
        mImgView_greatBot.setImageAlpha(0); // Hide the original image so you can see the animated images play..

        mImgView_badBot.setBackgroundResource(R.drawable.bothead_bad_animation_list);  //set to animation list xml file
        mImgView_goodBot.setBackgroundResource(R.drawable.bothead_good_animation_list);  //set to animation list xml file
        mImgView_greatBot.setBackgroundResource(R.drawable.bothead_great_animation_list);  //set to animation list xml file

        animateDrawable_badBot = (AnimationDrawable) mImgView_badBot.getBackground();   //set animator to imgView
        animateDrawable_goodBot = (AnimationDrawable) mImgView_goodBot.getBackground();   //set animator to imgView
        animateDrawable_greatBot = (AnimationDrawable) mImgView_greatBot.getBackground();   //set animator to imgView
    }

    public void startAnimations(){
        animateDrawable_badBot.start();
        animateDrawable_goodBot.start();
        animateDrawable_greatBot.start();

    }
}
