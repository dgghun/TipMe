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
    private TextView mTxtV_inputDisplay, mTxtV_dollarSign, mTxtV_numberCompCash;
    private final int BTN_CLR = 10, BTN_EQUALS = 11, BTN_TIMES = 12, BTN_DIVIDE = 13, BTN_PLUS = 14, BTN_MINUS = 15;
    private List<Integer> buttonIdsList;
    private List<Button> buttonsList;
    private int mFLAG_COMPUTATION_TYPE;
    private double mFirstNum, mSecondNum, mNumberCache;
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
        mTxtV_dollarSign = (TextView) view.findViewById(R.id.TextView_calculatorInputArea_DollarSign);
        mTxtV_dollarSign.setTypeface(typeface);
        mTxtV_numberCompCash = (TextView)view.findViewById(R.id.TextView_calculatorNumberStorageArea);
        mTxtV_numberCompCash.setText("");

        mFirstNum = -1;
        mSecondNum = 1;
        mFLAG_COMPUTATION_TYPE = 0;
        mNumberCache = Double.parseDouble(mTxtV_inputDisplay.getText().toString());
        mTxtV_inputDisplay.setText(decimalFormat.format(mNumberCache));

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
        CalcButtonInputHandler inputHandler = new CalcButtonInputHandler(view);
        String tempStr;
//        //Search for button id pressed
        for (int i = 0; i <= buttonIdsList.size(); i++) {

            if (id == buttonIdsList.get(i)) {   //If id found, i is the button pressed (0-9, X or $)

                if (i == BTN_CLR) {
                    tempStr = " ";
                    mTxtV_inputDisplay.setText(inputHandler.clear());
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
                        if(cacheNumStr.endsWith(mPLUS)){
                            Toast.makeText(view.getContext(), decimalFormat.format(Double.parseDouble(cacheNumStr.substring(0, cacheNumStr.length()-2))), Toast.LENGTH_SHORT).show();
//                            tempStr = decimalFormat.format(Double.parseDouble(cacheNumStr.substring(0, cacheNumStr.length()-2)))
                        }
                        else if(cacheNumStr.endsWith(mMINUS)){

                        }
                        else if(cacheNumStr.endsWith(mTIMES)){

                        }
                        else if(cacheNumStr.endsWith(mDIVIDE)){

                        }
                    }
                }
                else if (i == BTN_PLUS){
                    tempStr = mTxtV_inputDisplay.getText().toString() + " " + mMINUS;
                    mTxtV_numberCompCash.setText(tempStr);
                    mTxtV_inputDisplay.setText(decimalFormat.format(0.00));
                }
                else if (i == BTN_MINUS){
                    tempStr = mTxtV_inputDisplay.getText().toString() + " " + mMINUS;
                    mTxtV_numberCompCash.setText(tempStr);
                    mTxtV_inputDisplay.setText(decimalFormat.format(0.00));
                }
                else if (i == BTN_TIMES){
                    tempStr = mTxtV_inputDisplay.getText().toString() + " " + mTIMES;
                    mTxtV_numberCompCash.setText(tempStr);
                    mTxtV_inputDisplay.setText(decimalFormat.format(0.00));
                }
                else if (i == BTN_DIVIDE){
                    tempStr = mTxtV_inputDisplay.getText().toString() + " " + mDIVIDE;
                    mTxtV_numberCompCash.setText(tempStr);
                    mTxtV_inputDisplay.setText(decimalFormat.format(0.00));
                }
                else {   // i is a number so append it to current textView string
                    mTxtV_inputDisplay.setText(inputHandler.append(Integer.toString(i), mTxtV_inputDisplay.getText().toString()));
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


        int mLastBtn = 15;
        // Set button ids, button views and click listener
        for (int i = 0; i <= mLastBtn; i++) {
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
            else {
                buttonIdsList.add(getResources().getIdentifier("button_calculatorCalcNumber_" + i, "id", getActivity().getPackageName()));
                buttonsList.add((Button) view.findViewById(buttonIdsList.get(i)));
                buttonsList.get(i).setOnClickListener(this);
                buttonsList.get(i).setTypeface(typeface);
            }
        }
    }   //END of setUpButtons()
}
