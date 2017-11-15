package com.dgg.tipme;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HowWasSvcFragment extends Fragment implements View.OnClickListener{

    private View view;
    private TextView mTxtView_MainHeader;
    private Typewriter mWriter_header;
    private Button mBtn_great;
    private Button mBtn_good;
    private Button mBtn_bad;
    List<String> mList_greatList;
    List<String> mList_goodList;
    List<String> mList_badList;


    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        view = inflater.inflate(R.layout.fragment_how_was_svc, container, false);

        mBtn_great = (Button)view.findViewById(R.id.button_great);
        mBtn_good = (Button)view.findViewById(R.id.button_good);
        mBtn_bad = (Button)view.findViewById(R.id.button_bad);
        mBtn_great.setOnClickListener(this);
        mBtn_good.setOnClickListener(this);
        mBtn_bad.setOnClickListener(this);


        setUpTextArrays();


        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        setBtnsRandomText();
        setUpTextViewHeader();
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        Fragment fragment = new TipTotalsFragment();
        String service = "";

        if(id == mBtn_great.getId()){
            service = "20%";
        }
        else if(id == mBtn_good.getId()){
            service = "15%";
        }
        else if(id == mBtn_bad.getId()){
            service = "5%";
        }
        else fragment = null;

        MainActivity.Users_Service = service;

        if(fragment != null)
            ((MainActivity)getActivity()).replaceFragment(fragment, MainActivity.FRAG_HOW_WAS_SVC);

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
        mWriter_header.animateText(getResources().getString(R.string.str_textview_how_was_srvc), true);
    }   //END of setupTextViewHeader()

    /**
     * setRandomText()
     */
    private void setUpTextArrays(){
        mList_greatList = new ArrayList<>();
        mList_goodList = new ArrayList<>();
        mList_badList = new ArrayList<>();

        mList_greatList.add("Great");
        mList_greatList.add("Fantastic");
        mList_greatList.add("Too good");
        mList_greatList.add("Wonderful");
        mList_greatList.add("Excellent");
        mList_greatList.add("Awesome");
        mList_greatList.add("Like WOW");

        mList_goodList.add("Good");
        mList_goodList.add("Worthy");
        mList_goodList.add("Favorable");
        mList_goodList.add("Positive");
        mList_goodList.add("Pleasing");
        mList_goodList.add("Admirable");
        mList_goodList.add("Nice");

        mList_badList.add("Bad");
        mList_badList.add("Awful");
        mList_badList.add("Terrible");
        mList_badList.add("Offensive");
        mList_badList.add("Horrible");
        mList_badList.add("Dreadful");
        mList_badList.add("The worst");
    }

    private void setBtnsRandomText(){
        try {
            mBtn_great.setText(mList_greatList.get((new Random()).nextInt(mList_greatList.size())));
            mBtn_good.setText(mList_goodList.get((new Random()).nextInt(mList_goodList.size())));
            mBtn_bad.setText(mList_badList.get((new Random()).nextInt(mList_badList.size())));
        }catch (Exception e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
