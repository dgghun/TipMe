package com.dgg.tipme;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;


public class MainFragment extends Fragment {

    private View view;
    private Button Btn_Tip;
    private Button Btn_Calculator;
    private TextView TxtView_MainHeader;

    Typewriter writer_header;   //Custom class to animate textView text like a "type writer"

    AnimationDrawable animateDrawable_TipBotBlink;
    ImageView ImgView_TipBot;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){
        view = inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));  //hide icon in actionbar
        setUpButtons();
        setUpTipBotImageView();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        FragmentManager fm = getFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);    // Clear back stack of fragments.

        setUpTextViewHeader();                          // Animates header
        animateDrawable_TipBotBlink.start();    // Animate tipbot blinking

    }



    /*** METHODS *******************************************************************************************************/

    /** setUpButtons()
     *
     */
    public void setUpButtons(){

        // Get handle on buttons
        Btn_Tip = (Button)view.findViewById(R.id.button_Tip);
        Btn_Calculator = (Button)view.findViewById(R.id.button_Calculator);


        // Tip button pressed
        Btn_Tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment transaction - get bill amount
                Fragment fragment = new BillAmountFragment();
                ((MainActivity)getActivity()).replaceFragment(fragment, MainActivity.FRAG_MAIN_FRAG);

            }
        });

        Btn_Calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CalculatorFragment();
                ((MainActivity)getActivity()).replaceFragment(fragment, MainActivity.FRAG_CALCULATOR);
            }
        });

    }


    /** setUpTipBotImageView()
     *
     */
    public void setUpTipBotImageView(){
        ImgView_TipBot =(ImageView)view.findViewById(R.id.imageView_TipBotHead);
        ImgView_TipBot.setImageAlpha(0); // Hide the original image so you can see the animated images play..
        ImgView_TipBot.setBackgroundResource(R.drawable.tipbot_head_blink_animation_list);  //set to animation list xml file
        animateDrawable_TipBotBlink = (AnimationDrawable) ImgView_TipBot.getBackground();   //set animator to imgView
    }


    /** setUpTextViewHeader()
     *
     */
    public void setUpTextViewHeader() {
        TxtView_MainHeader = (TextView) view.findViewById(R.id.TextView_mainScreenHeader);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DS-DIGIB.TTF");  //get custom typeface from assets
        TxtView_MainHeader.setTypeface(typeface);   //set header to custom typeFace

        //Add a character every few seconds
        writer_header = (Typewriter) TxtView_MainHeader;
        writer_header.setCharacterDelay(50);
        writer_header.animateText(getResources().getString(R.string.str_textview_how_can_i_help), true);
    }

}



