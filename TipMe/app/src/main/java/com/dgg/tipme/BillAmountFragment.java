package com.dgg.tipme;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BillAmountFragment extends Fragment {

    private View view;
    private TextView mTxtView_MainHeader;
    private EditText mEditTxt_billInput;
    Typewriter mWriter_header;   //Custom class to animate textView text like a "type writer"

    private Button mBtn_1, mBtn_2, mBtn_3, mBtn_4, mBtn_5, mBtn_6, mBtn_7, mBtn_8, mBtn_9, mBtn_0, mBtn_x, mBtn_$;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        view = inflater.inflate(R.layout.fragment_bill_amount, container, false);


        mEditTxt_billInput = (EditText) view.findViewById(R.id.EditText_enterBillAmountArea);

        setUpButtons();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        setUpTextViewHeader();
    }


    //******************* Methods *********************//

    public void setUpButtons(){

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button_calcNumber_$:
                        Toast.makeText(view.getContext(), "Btn $", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_x:
                        Toast.makeText(view.getContext(), "Btn x", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_1:
                        Toast.makeText(view.getContext(), "Btn 1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_2:
                        Toast.makeText(view.getContext(), "Btn 2", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_3:
                        Toast.makeText(view.getContext(), "Btn 3", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_4:
                        Toast.makeText(view.getContext(), "Btn 4", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_5:
                        Toast.makeText(view.getContext(), "Btn 5", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_6:
                        Toast.makeText(view.getContext(), "Btn 6", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_7:
                        Toast.makeText(view.getContext(), "Btn 7", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_8:
                        Toast.makeText(view.getContext(), "Btn 8", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_9:
                        Toast.makeText(view.getContext(), "Btn 9", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.button_calcNumber_0:
                        Toast.makeText(view.getContext(), "Btn 0", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        mBtn_$ = (Button) view.findViewById(R.id.button_calcNumber_$);
        mBtn_x = (Button) view.findViewById(R.id.button_calcNumber_x);
        mBtn_1 = (Button) view.findViewById(R.id.button_calcNumber_1);
        mBtn_2 = (Button) view.findViewById(R.id.button_calcNumber_2);
        mBtn_3 = (Button) view.findViewById(R.id.button_calcNumber_3);
        mBtn_4 = (Button) view.findViewById(R.id.button_calcNumber_4);
        mBtn_5 = (Button) view.findViewById(R.id.button_calcNumber_5);
        mBtn_6 = (Button) view.findViewById(R.id.button_calcNumber_6);
        mBtn_7 = (Button) view.findViewById(R.id.button_calcNumber_7);
        mBtn_8 = (Button) view.findViewById(R.id.button_calcNumber_8);
        mBtn_9 = (Button) view.findViewById(R.id.button_calcNumber_9);
        mBtn_0 = (Button) view.findViewById(R.id.button_calcNumber_0);

        mBtn_$.setOnClickListener(clickListener);
        mBtn_x.setOnClickListener(clickListener);
        mBtn_1.setOnClickListener(clickListener);
        mBtn_2.setOnClickListener(clickListener);
        mBtn_3.setOnClickListener(clickListener);
        mBtn_4.setOnClickListener(clickListener);
        mBtn_5.setOnClickListener(clickListener);
        mBtn_6.setOnClickListener(clickListener);
        mBtn_7.setOnClickListener(clickListener);
        mBtn_8.setOnClickListener(clickListener);
        mBtn_9.setOnClickListener(clickListener);
        mBtn_0.setOnClickListener(clickListener);


    }



    /**
     * setUpTextviewHeader()
     */
    public void setUpTextViewHeader() {
        mTxtView_MainHeader = (TextView) view.findViewById(R.id.TextView_whatIsBillAmount);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DS-DIGIB.TTF");  //get custom typeface from assets
        mTxtView_MainHeader.setTypeface(typeface);   //set header to custom typeFace
        mTxtView_MainHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35); //change text size in SP

        //Animate text header
        mWriter_header = (Typewriter) mTxtView_MainHeader;
        mWriter_header.setCharacterDelay(50);
        mWriter_header.animateText(getResources().getString(R.string.str_textview_what_is_bill_amount), false);
    }
}
