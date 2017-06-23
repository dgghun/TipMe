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

import java.util.ArrayList;
import java.util.List;

public class BillAmountFragment extends Fragment {

    private View view;
    private TextView mTxtView_MainHeader;
    private EditText mEditTxt_billInput;
    private Typewriter mWriter_header;   //Custom class to animate textView text like a "type writer"
    private final int x = 10, $ = 11;
    private final List<Integer> buttonIds = new ArrayList<>();
    private final List<Button> buttons = new ArrayList<>();


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

    /**
     * setUpButtons
     */
    public void setUpButtons(){

        // Button click listener
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                ButtonInputHandler inputHandler = new ButtonInputHandler();

                for (int i = 0; i <= buttonIds.size(); i++) {

                    if (id == buttonIds.get(i)) {
                        if (i == x)
                            mEditTxt_billInput.setText(inputHandler.delete(mEditTxt_billInput.getText().toString()));
                        else if (i == $)
                            Toast.makeText(view.getContext(), "$ = GitTip", Toast.LENGTH_SHORT).show();
                        else
                            mEditTxt_billInput.setText(inputHandler.append(Integer.toString(i), mEditTxt_billInput.getText().toString()));

                        i = buttonIds.size() + 1; // break from loop
                    }
                }
            }
        };

        // Set button ids, button views and click listener
        for(int i = 0; i <= 11; i++) {
            if(i == x)
            {
                buttonIds.add(getResources().getIdentifier("button_calcNumber_x", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(x)));
                buttons.get(x).setOnClickListener(clickListener);
            }
            else if (i == $)
            {
                buttonIds.add(getResources().getIdentifier("button_calcNumber_$", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get($)));
                buttons.get($).setOnClickListener(clickListener);
            }
            else {
                buttonIds.add(getResources().getIdentifier("button_calcNumber_" + i, "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(i)));
                buttons.get(i).setOnClickListener(clickListener);
            }
        }
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
