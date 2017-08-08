package com.dgg.tipme;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class BillAmountFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView mTxtView_MainHeader;
    private EditText mEditTxt_billInput;
    private Typewriter mWriter_header;   //Custom class to animate textView text like a "type writer"
    private final int DEL_BTN = 10, GETTIP_BTN = 11;
    private final List<Integer> buttonIds = new ArrayList<>();
    private final List<Button> buttons = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstance) {
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
    public void onStart() {
        super.onStart();
        setUpTextViewHeader();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        ButtonInputHandler inputHandler = new ButtonInputHandler(view);

        //Search for button id pressed
        for (int i = 0; i <= buttonIds.size(); i++) {

            if (id == buttonIds.get(i)) {   //If id found, i is the button pressed (0-9, X or $)

                if (i == DEL_BTN)
                    mEditTxt_billInput.setText(inputHandler.delete(mEditTxt_billInput.getText().toString()));
                else if (i == GETTIP_BTN) {
                    Fragment fragment = new TipAmountFragment();
                    ((MainActivity) getActivity()).replaceFragment(fragment, MainActivity.FRAG_BILL_AMOUNT); // Start TipAmountFragment
                } else    // i is a number so append it to current textView string
                    mEditTxt_billInput.setText(inputHandler.append(Integer.toString(i), mEditTxt_billInput.getText().toString()));

                i = buttonIds.size() + 1; // break from loop
            }
        }
    }


    //******************* Methods *********************//


    /**
     * setUpButtons
     */
    public void setUpButtons() {

        int mNumberOfButtons = 11;
        // Set button ids, button views and click listener
        for (int i = 0; i <= mNumberOfButtons; i++) {
            if (i == DEL_BTN) {
                buttonIds.add(getResources().getIdentifier("button_calcNumber_x", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(DEL_BTN)));
                buttons.get(DEL_BTN).setOnClickListener(this);
            } else if (i == GETTIP_BTN) {
                buttonIds.add(getResources().getIdentifier("button_calcNumber_$", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(GETTIP_BTN)));
                buttons.get(GETTIP_BTN).setOnClickListener(this);
            } else {
                buttonIds.add(getResources().getIdentifier("button_calcNumber_" + i, "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(i)));
                buttons.get(i).setOnClickListener(this);
            }
        }
    }   //END of setUpButtons()


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
    }   //END of setupTextViewHeader()
}
