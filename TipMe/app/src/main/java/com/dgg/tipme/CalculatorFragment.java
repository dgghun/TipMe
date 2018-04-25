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

import java.io.IOException;
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


    private final int MAX_INPUT_LENGTH = 10, MAX_OPERATIONS = 9;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");    // formats 2 decimal places
    private String mStr_PLUS = "\u002B", mStr_MINUS = "\u2212", mStr_TIMES = "\u00D7", mStr_DIVIDE = "\u00F7", mStr_EQUALS = "\u003D";


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

        // Equal sign in cash area means a calculation happened already so remove it.
        if(mTxtV_numberCompCash.getText().toString().equals(mStr_EQUALS)){
            tempStr = "";
            mTxtV_numberCompCash.setText(tempStr);
        }

        //Search for button id pressed
        for (int buttonId = 0; buttonId <= buttonIdsList.size(); buttonId++) {

            if (id == buttonIdsList.get(buttonId)) {   //If id found, i is one of the buttons pressed

                if (buttonId == BTN_CLR) {
                    tempStr = " ";
                    mTxtV_inputDisplay.setText(tempStr);
                    mTxtV_numberCompCash.setText(tempStr);

                }
                else if (buttonId == BTN_EQUALS) {

//                    if (mArray_inputOperations.size() <= 1 && mTxtV_inputDisplay.getText().toString().trim().isEmpty()){
//                        Toast.makeText(view.getContext(), "No numbers to compute.", Toast.LENGTH_SHORT).show();
//                    }
//                    else if (mTxtV_inputDisplay.getText().toString().trim().isEmpty() && mArray_inputOperations.size() > 2){
//
//                        //If input is blank & there are operations, there is a trailing operator error.
//                        tempStr = "Input Error";
//                        mTxtV_numberCompCash.setText(tempStr);
//                        tempStr = " ";
//                        mTxtV_inputDisplay.setText(tempStr);
//                        mArray_inputOperations.clear();
//                        Toast.makeText(view.getContext(), "Trailing operator error.", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        calculateAndUpdateViews();
//                    }
                }
                else if (buttonId == BTN_PLUS){

                    operatorCalculation(mStr_PLUS);

                }
                else if (buttonId == BTN_MINUS){

                    operatorCalculation(mStr_MINUS);

                }
                else if (buttonId == BTN_DIVIDE){

                    operatorCalculation(mStr_DIVIDE);

                }
                else if (buttonId == BTN_TIMES){
                    operatorCalculation(mStr_TIMES);

                }
                else if (buttonId == BTN_DECIMAL){
                    tempStr = mTxtV_inputDisplay.getText().toString();

                    // Check if a decimal can be added to string
                    if(!tempStr.contains(".") && tempStr.length() < MAX_INPUT_LENGTH){

                        if(tempStr.trim().isEmpty())
                            tempStr = "0.";
                        else
                            tempStr = tempStr + ".";
                        mTxtV_inputDisplay.setText(tempStr);
                    }

                }
                else {   // i is a number so append it to current textView string
                    tempStr = mTxtV_inputDisplay.getText().toString();

                    if(tempStr.length() >= MAX_INPUT_LENGTH){}  //Do nothing. Max characters reached
                    else if(buttonId == 0 && tempStr.startsWith("0") && !tempStr.contains(".")) {} // Do nothing. No leading zeros
                    else{
                        // No leading zeros. Replace with input digit
                        if(tempStr.equals("0")){
                            tempStr = Integer.toString(buttonId);
                        }
                        else {
                            tempStr = tempStr + Integer.toString(buttonId);
                        }

                        mTxtV_inputDisplay.setText(tempStr);
                    }
                }
                buttonId = buttonIdsList.size() + 1; // break from loop
            }
        }
    }





    private Boolean cacheIsEmpty(){
        return (mTxtV_numberCompCash.getText().toString().trim().length() <= 1);
    }




    private Boolean cacheContainsOperator(){
        String cache = mTxtV_numberCompCash.getText().toString();
        return (cache.contains(mStr_DIVIDE) || cache.contains(mStr_TIMES) || cache.contains(mStr_PLUS) || cache.contains(mStr_MINUS) || cache.contains(mStr_EQUALS));
    }
    private void calculateNumbers(String operator){
        Toast.makeText(view.getContext(), "calculateNumebrs()", Toast.LENGTH_SHORT).show();
        if(mTxtV_numberCompCash.getText().toString().trim().endsWith(operator)){

        }
    }


    private void operatorCalculation(String operator){

        String tempStr;

        if(cacheIsEmpty()){
            tempStr = mTxtV_inputDisplay.getText().toString() + " " + operator;
            mTxtV_numberCompCash.setText(tempStr);
            tempStr = " ";
            mTxtV_inputDisplay.setText(tempStr);
        }
        else if(cacheContainsOperator() && mTxtV_inputDisplay.getText().toString().trim().length() <= 0){
            tempStr = mTxtV_numberCompCash.getText().toString().trim();
            tempStr = tempStr.substring(0, tempStr.length() - 2) + " " + operator;
            mTxtV_numberCompCash.setText(tempStr);
        }
        else{

            Double total = calculateCahceAndInput();

            // if no errors in calculation
            if(total >= 0.0){
                tempStr = stripTrailingZeros(Double.toString(total)) + " " + operator;
                mTxtV_numberCompCash.setText(tempStr);
                tempStr = " ";
                mTxtV_inputDisplay.setText(tempStr);
            }else{
                tempStr = " ";
                mTxtV_inputDisplay.setText(tempStr);
                mTxtV_numberCompCash.setText(tempStr);
            }

        }
    }


    /**
     *
     * @return double or -1 if division by zero
     */
    private double calculateCahceAndInput(){

        Double total = 0.0;
        String cahceStr = mTxtV_numberCompCash.getText().toString().trim();
        String operator = cahceStr.substring(cahceStr.length() - 1);
        Double num1 = Double.parseDouble(cahceStr.substring(0, cahceStr.length() - 2));
        Double num2 = Double.parseDouble(mTxtV_inputDisplay.getText().toString().trim());

        if (operator.equals(mStr_PLUS)){
            total = num1 + num2;
        }
        else if (operator.equals(mStr_MINUS)){
            total = num1 - num2;
        }
        else if (operator.equals(mStr_TIMES)){
            total = num1 * num2;
        }
        else if (operator.equals(mStr_DIVIDE)){

            if(num2 <= 0.0) {
                Toast.makeText(view.getContext(), "Can't divide by zero.", Toast.LENGTH_SHORT).show();
                return -1.0;
            }
            else
                total = num1 / num2;
        }

        return total;
    }


    private String stripTrailingZeros(String number){
        return number.replaceAll("()\\.0+$|(\\..+?)0+$", "");
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




}


