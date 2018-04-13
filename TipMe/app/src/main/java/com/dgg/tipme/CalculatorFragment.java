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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculatorFragment extends Fragment implements View.OnClickListener  {

    private View view;
    private Typeface typeface;
    private TextView mTxtV_inputDisplay, mTxtV_numberCompCash;
    private final int BTN_CLR = 10, BTN_EQUALS = 11, BTN_TIMES = 12, BTN_DIVIDE = 13, BTN_PLUS = 14, BTN_MINUS = 15, BTN_DECIMAL = 16;
    private final int LAST_BTN = BTN_DECIMAL;
    private List<Integer> buttonIdsList;
    private List<Button> buttonsList;

    private final int MAX_INPUT_LENGTH = 12;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");    // formats 2 decimal places
    private String mPLUS = "+", mMINUS = "-", mTIMES = "x", mDIVIDE = "/";

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        view = inflater.inflate(R.layout.fragment_calculator, container, false);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DS-DIGIB.TTF");  //get custom typeface from assets

        mTxtV_inputDisplay = (TextView)view.findViewById(R.id.TextView_calculatorInputDisplay);
        mTxtV_inputDisplay.setTypeface(typeface);

        mTxtV_numberCompCash = (TextView)view.findViewById(R.id.TextView_calculatorNumberStorageArea);
        mTxtV_numberCompCash.setText("");


        setUpButtons();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        setUpButtons();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        String tempStr;

        //Search for button id pressed
        for (int i = 0; i <= buttonIdsList.size(); i++) {

            if (id == buttonIdsList.get(i)) {   //If id found, i is one of the buttons pressed

                if (i == BTN_CLR) {
                    tempStr = " ";
                    mTxtV_inputDisplay.setText("0");
                    mTxtV_numberCompCash.setText(tempStr);
                }
                else if (i == BTN_EQUALS) {
                    String cacheNumStr = mTxtV_numberCompCash.getText().toString();
                    String inputDisplayStr = mTxtV_inputDisplay.getText().toString();

                    if(cacheNumStr.length() <= 1){
                        // Do nothing if there isn't a number to compute
                    }
                    else if (inputDisplayStr.equals("0.00") && cacheNumStr.equals("0.00 /")){
                        Toast.makeText(view.getContext(), "Result is undefined.", Toast.LENGTH_SHORT).show();
                    }
                    else if(inputDisplayStr.equals("0.00")){
                        Toast.makeText(view.getContext(), "Can't divide by zero.", Toast.LENGTH_SHORT).show();
                    }
                    else{

                    }
                }
                else if (i == BTN_PLUS || i == BTN_MINUS || i == BTN_DIVIDE || i == BTN_TIMES){
                    tempStr = mTxtV_inputDisplay.getText().toString() + " " + mMINUS;
                    mTxtV_numberCompCash.setText(tempStr);
                    mTxtV_inputDisplay.setText("0");
                }
                else if (i == BTN_DECIMAL){
                    tempStr = mTxtV_inputDisplay.getText().toString();


                    if(!tempStr.contains(".") && tempStr.length() < MAX_INPUT_LENGTH){
                        tempStr = tempStr + ".";
                        mTxtV_inputDisplay.setText(tempStr);
                    }
                }
                else {   // i is a number so append it to current textView string
                    tempStr = mTxtV_inputDisplay.getText().toString();

                    if(tempStr.length() >= MAX_INPUT_LENGTH){}  //Do nothing. Max characters reached
                    else if(i == 0 && tempStr.startsWith("0") && !tempStr.contains(".")) {} // Do nothing. No leading zeros
                    else{
                        // No leading zeros. Replace with input digit
                        if(tempStr.equals("0")){
                            tempStr = Integer.toString(i);
                        }
                        else {
                            tempStr = tempStr + Integer.toString(i);
                        }

                        mTxtV_inputDisplay.setText(tempStr);
                    }
                }
                i = buttonIdsList.size() + 1; // break from loop
            }
        }
    }



    /**
     * setUpButtons
     */
    private void setUpButtons() {

        buttonIdsList = new ArrayList<>();
        buttonsList = new ArrayList<>();


        // Set button ids, button views and click listener
        for (int i = 0; i <= LAST_BTN; i++) {
            if (i == BTN_CLR) {
                buttonIdsList.add(getResources().getIdentifier("button_calculatorCalcNumber_delete", "id", getActivity().getPackageName()));
                buttonsList.add((Button) view.findViewById(buttonIdsList.get(BTN_CLR)));
                buttonsList.get(BTN_CLR).setOnClickListener(this);
                buttonsList.get(BTN_CLR).setTypeface(typeface);
            }
            else if (i == BTN_EQUALS) {
                buttonIdsList.add(getResources().getIdentifier("button_calculatorCalcNumber_equals", "id", getActivity().getPackageName()));
                buttonsList.add((Button) view.findViewById(buttonIdsList.get(BTN_EQUALS)));
                buttonsList.get(BTN_EQUALS).setOnClickListener(this);
                buttonsList.get(BTN_EQUALS).setTypeface(typeface);
            }
            else if (i == BTN_PLUS){
                buttonIdsList.add(getResources().getIdentifier("button_calculatorCalcNumber_plus", "id", getActivity().getPackageName()));
                buttonsList.add((Button) view.findViewById(buttonIdsList.get(BTN_PLUS)));
                buttonsList.get(BTN_PLUS).setOnClickListener(this);
                buttonsList.get(BTN_PLUS).setTypeface(typeface);
            }
            else if (i == BTN_MINUS){
                buttonIdsList.add(getResources().getIdentifier("button_calculatorCalcNumber_minus", "id", getActivity().getPackageName()));
                buttonsList.add((Button) view.findViewById(buttonIdsList.get(BTN_MINUS)));
                buttonsList.get(BTN_MINUS).setOnClickListener(this);
                buttonsList.get(BTN_MINUS).setTypeface(typeface);
            }
            else if (i == BTN_TIMES){
                buttonIdsList.add(getResources().getIdentifier("button_calculatorCalcNumber_times", "id", getActivity().getPackageName()));
                buttonsList.add((Button) view.findViewById(buttonIdsList.get(BTN_TIMES)));
                buttonsList.get(BTN_TIMES).setOnClickListener(this);
                buttonsList.get(BTN_TIMES).setTypeface(typeface);
            }
            else if (i == BTN_DIVIDE){
                buttonIdsList.add(getResources().getIdentifier("button_calculatorCalcNumber_divide", "id", getActivity().getPackageName()));
                buttonsList.add((Button) view.findViewById(buttonIdsList.get(BTN_DIVIDE)));
                buttonsList.get(BTN_DIVIDE).setOnClickListener(this);
                buttonsList.get(BTN_DIVIDE).setTypeface(typeface);
            }
            else if(i == BTN_DECIMAL){
                buttonIdsList.add(getResources().getIdentifier("button_calculatorDecimal", "id", getActivity().getPackageName()));
                buttonsList.add((Button) view.findViewById(buttonIdsList.get(BTN_DECIMAL)));
                buttonsList.get(BTN_DECIMAL).setOnClickListener(this);
                buttonsList.get(BTN_DECIMAL).setTypeface(typeface);
            }
            else {
                buttonIdsList.add(getResources().getIdentifier("button_calculatorCalcNumber_" + i, "id", getActivity().getPackageName()));
                buttonsList.add((Button) view.findViewById(buttonIdsList.get(i)));
                buttonsList.get(i).setOnClickListener(this);
                buttonsList.get(i).setTypeface(typeface);
            }
        }
    }   //END of setUpButtons()


    private void computeEquals(){

    }


    private void computePlus(){

    }


    private void computeMinus(){

    }


    private void computeTimes(){

    }


    private void computeDivide(){

    }



}


