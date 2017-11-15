package com.dgg.tipme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Stack;


public class TipTotalsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button mBtn_splitMinus, mBtn_splitPlus, mBtn_tipPercentMinus, mBtn_tipPercentPlus, mBtn_rndUp, mBtn_rndDown, mBtn_Calc, mBtn_Home, mBtn_Quit;
    private TextView mTxtV_Bill, mTxtV_splitBill, mTxtV_splitCount, mTxtV_tipPercent, mTxtV_tip, mTxtV_total;

    private final int MAX_SPLIT_NUM = 100;
    private final int MIN_SPLIT_NUM = 1;
    private final Double MAX_PERCENT_TIP = 0.99;
    private final Double MIN_PERCENT_TIP = 0.00;
    private final String BILL_MAX = "$999,999.99";
    private final String BILL_MIN = "$1.00";

    private final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("###,###,##0.00");


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.fragment_tip_totals, container, false);

        setUpButtons_TxtViews();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Set text views
        mTxtV_Bill.setText(doubleToMoneyString(Double.parseDouble(MainActivity.Users_Bill)));
        mTxtV_splitBill.setText(MainActivity.Users_Bill);
        mTxtV_tipPercent.setText(MainActivity.Users_Service);

        updateTipTotalAndSplitBill(percentStringToDouble(MainActivity.Users_Service), 1); //initial split is 1

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBtn_splitMinus.getId()) {
            // if able to decrement, update bill info with decremented split count
            int splitCount = Integer.parseInt(mTxtV_splitCount.getText().toString());
            if(canDecrementSplitCount(splitCount)) {
                String newSplitCountStr = Integer.toString(splitCount - 1);
                mTxtV_splitCount.setText(newSplitCountStr);
                updateTipTotalAndSplitBill(percentStringToDouble(mTxtV_tipPercent.getText().toString()), splitCount - 1);
            }

        } else if (id == mBtn_splitPlus.getId()) {
            // Get incremented split count
            int newSplitCount = Integer.parseInt(mTxtV_splitCount.getText().toString()) + 1;

            // Check if you can increment split count,if so, update split count and bill info.
            if (canIncrementSplitCount(newSplitCount)) {
                String newSplitCountStr = Integer.toString(newSplitCount);
                mTxtV_splitCount.setText(newSplitCountStr);
                updateTipTotalAndSplitBill(percentStringToDouble(mTxtV_tipPercent.getText().toString()), newSplitCount);
            }

        } else if (id == mBtn_tipPercentMinus.getId()) {
            Double newTipPercent = percentStringToDouble(mTxtV_tipPercent.getText().toString()) - 0.01; // Get decremented percent

            //check if can decrement percent
            if(canIncrementOrDecrementTipPercent(newTipPercent)){
                String newTipPercentStr = doubleToPercentString(newTipPercent);
                mTxtV_tipPercent.setText(newTipPercentStr);
                updateTipTotalAndSplitBill(newTipPercent, Integer.parseInt(mTxtV_splitCount.getText().toString()));
            }

        } else if (id == mBtn_tipPercentPlus.getId()) {
            Double newTipPercent = percentStringToDouble(mTxtV_tipPercent.getText().toString()) + 0.01; // Get decremented percent

            //check if can decrement percent
            if(canIncrementOrDecrementTipPercent(newTipPercent)){
                String newTipPercentStr = doubleToPercentString(newTipPercent);
                mTxtV_tipPercent.setText(newTipPercentStr);
                updateTipTotalAndSplitBill(newTipPercent, Integer.parseInt(mTxtV_splitCount.getText().toString()));
            }

        } else if (id == mBtn_rndUp.getId()) {
            final Double ONE_DOLLAR = 1.00;

            if(canRoundTipUpOrDown(ONE_DOLLAR)) {
                Double splitBill = moneyStringToDouble(mTxtV_splitBill.getText().toString());
                Double newTip = getRoundedTip(ONE_DOLLAR);

                mTxtV_tip.setText(doubleToMoneyString(newTip));
                mTxtV_total.setText(doubleToMoneyString(splitBill + newTip));

            }

        } else if (id == mBtn_rndDown.getId()) {
            final Double NEGATIVE_ONE_DOLLAR = -1.00;

            if(canRoundTipUpOrDown(NEGATIVE_ONE_DOLLAR)) {
                Double splitBill = moneyStringToDouble(mTxtV_splitBill.getText().toString());
                Double newTip = getRoundedTip(NEGATIVE_ONE_DOLLAR);

                mTxtV_tip.setText(doubleToMoneyString(newTip));
                mTxtV_total.setText(doubleToMoneyString(splitBill + newTip));

            }

        } else if (id == mBtn_Calc.getId()) {

            Toast.makeText(view.getContext(), "Coming soon!", Toast.LENGTH_SHORT).show();

        } else if (id == mBtn_Home.getId()) {
            Fragment fragment = new MainFragment();
            ((MainActivity) getActivity()).replaceFragment(fragment, MainActivity.FRAG_TIP_TOTALS); // Start HowWasSvcFragment

        } else if (id == mBtn_Quit.getId()) {

            getActivity().finish();
        }
    }


    //**** METHODS ****//

    private void setUpButtons_TxtViews() {
        mBtn_splitMinus = (Button) view.findViewById(R.id.button_minus_SplitTip);
        mBtn_splitPlus = (Button) view.findViewById(R.id.button_plus_SplitTip);
        mBtn_tipPercentMinus = (Button) view.findViewById(R.id.button_minus_TipPercent);
        mBtn_tipPercentPlus = (Button) view.findViewById(R.id.button_plus_TipPercent);
        mBtn_rndDown = (Button) view.findViewById(R.id.button_Totals_roundDown);
        mBtn_rndUp = (Button) view.findViewById(R.id.button_Totals_roundUp);
        mBtn_Calc = (Button) view.findViewById(R.id.button_Totals_Calculator);
        mBtn_Home = (Button) view.findViewById(R.id.button_Totals_Home);
        mBtn_Quit = (Button) view.findViewById(R.id.button_Totals_Quit);

        mBtn_splitMinus.setOnClickListener(this);
        mBtn_splitPlus.setOnClickListener(this);
        mBtn_tipPercentMinus.setOnClickListener(this);
        mBtn_tipPercentPlus.setOnClickListener(this);
        mBtn_rndDown.setOnClickListener(this);
        mBtn_rndUp.setOnClickListener(this);
        mBtn_Calc.setOnClickListener(this);
        mBtn_Home.setOnClickListener(this);
        mBtn_Quit.setOnClickListener(this);

        mTxtV_Bill = (TextView) view.findViewById(R.id.textView_Bill_Digits);
        mTxtV_splitBill = (TextView) view.findViewById(R.id.textView_SplitBill_Digits);
        mTxtV_splitCount = (TextView) view.findViewById(R.id.textView_Split_Digit);
        mTxtV_tipPercent = (TextView) view.findViewById(R.id.textView_TipPecent_Digit);
        mTxtV_tip = (TextView) view.findViewById(R.id.textView_Tip_Digits);
        mTxtV_total = (TextView) view.findViewById(R.id.textView_Total_Digits);

    }



    public Double percentStringToDouble(String percentStr){

        return Double.parseDouble(percentStr.substring(0, percentStr.indexOf('%'))) / 100;
    }



    public Double moneyStringToDouble(String moneyStr){

        return Double.parseDouble(removeMoneyAndCommaChars(moneyStr));
    }



    public String doubleToMoneyString(Double doubleNum){

        return "$" + DECIMAL_FORMATTER.format(doubleNum);
    }



    public String doubleToPercentString(Double doubleNum){
        DecimalFormat decimalFormat = new DecimalFormat("00.00");
        String temp = decimalFormat.format(doubleNum * 100);

        return temp.substring(0, temp.indexOf('.')) + "%";
    }



    public String removeMoneyAndCommaChars(String str) {

        // Remove money sign
        if (str.charAt(0) == '$') {
            str = str.substring(str.indexOf('$') + 1, str.length());
        }

        //Remove Commas
        String tempStr = "";
        for (int i = 0; i < str.length(); i++) {

            if (str.charAt(i) != ',')
                tempStr = tempStr + str.charAt(i);
        }

        return tempStr;
    }



    public Boolean canIncrementSplitCount(int splitCount){

        String error_splitRange = "Split range is " + MIN_SPLIT_NUM + " to " + MAX_SPLIT_NUM + ".";

        // Check if current split digit is at max
        if (splitCount >= MAX_SPLIT_NUM) {
            Toast.makeText(view.getContext(), error_splitRange, Toast.LENGTH_SHORT).show();
            return false;   // err, split digit at max
        }

        // Check if divided bill doesn't cross minimum amount
        if( divideBill(splitCount, moneyStringToDouble(mTxtV_Bill.getText().toString())) <  moneyStringToDouble(BILL_MIN)) {
            Toast.makeText(view.getContext(), "Can not split any lower.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    public Boolean canDecrementSplitCount(int splitCount){
        if(splitCount <= MIN_SPLIT_NUM){
            Toast.makeText(view.getContext(), "Split range is " + MIN_SPLIT_NUM + " to " + MAX_SPLIT_NUM + ".", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    public Boolean canIncrementOrDecrementTipPercent(Double newTipPercent){
        if (newTipPercent > MAX_PERCENT_TIP){
            Toast.makeText(view.getContext(), "Can't increase percent any higher.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(newTipPercent < MIN_PERCENT_TIP){
            Toast.makeText(view.getContext(), "Can't decrease percent any lower.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }



    public Boolean canRoundTipUpOrDown(Double numToRoundBy){
        final Double MIN_TIP_AMOUNT = 0.00;
        final Double CURRENT_TIP = moneyStringToDouble(mTxtV_tip.getText().toString());

        // If rounding down and current tip is just cents.
        if(CURRENT_TIP > 0.0 && CURRENT_TIP < 1.0 && numToRoundBy < 1.00 )
            return true;

        if(CURRENT_TIP + numToRoundBy > moneyStringToDouble(BILL_MAX)){
            Toast.makeText(view.getContext(), "Can't round any higher.", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if (CURRENT_TIP + numToRoundBy < MIN_TIP_AMOUNT){
            Toast.makeText(view.getContext(), "Can't round any lower.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;

    }



    public Double getRoundedTip(Double numToRoundBy){
        final Double ONE_DOLLAR = 1.00;
        String currentTipStr = mTxtV_tip.getText().toString();
        Double currentTipDbl = moneyStringToDouble(currentTipStr);
        Double newTip;

        // If the current tip has cents, round to nearest dollar
        if(!currentTipStr.endsWith(".00")){
            currentTipStr = currentTipStr.substring(0, currentTipStr.length()-3) + ".00";   //remove cents from current tip

            if(numToRoundBy >= ONE_DOLLAR)
                newTip = moneyStringToDouble(currentTipStr) + ONE_DOLLAR; //If rounding up, round up by 1
             else
                newTip = moneyStringToDouble(currentTipStr);    // Else must be rounding down to nearest dollar
        } else{
            // Current tip has no cents so round up or down by one dollar
            if(numToRoundBy >= ONE_DOLLAR)
                newTip = currentTipDbl + ONE_DOLLAR; //If rounding up, round up by 1
            else
                newTip = currentTipDbl - ONE_DOLLAR;    // Else must be rounding down to nearest dollar
        }

        return newTip;
    }



    public Double divideBill(int divisor, Double bill){

        return bill / (double) divisor;
    }



    public void updateTipTotalAndSplitBill(Double tipPercent, int splitCount){

        try {
            // Calculate splitBill, tip and total amounts
            Double bill = moneyStringToDouble(mTxtV_Bill.getText().toString()); // Get bill
            Double splitBill = bill / (double) splitCount;  //Get how much split bill is (bill per person)
            Double tipDbl;
            if(tipPercent.equals(MIN_PERCENT_TIP)) tipDbl = MIN_PERCENT_TIP;           // If tip is 0%, no tip.
            else tipDbl  =  splitBill * tipPercent;         //Get how much tip is of split bill (per person)
            Double billTotalDbl = splitBill + tipDbl;    //Get how much total is per person


            //Convert doubles to strings with dollar signs
            String splitBillStr = doubleToMoneyString(splitBill);
            String tipStr = doubleToMoneyString(tipDbl);
            String billTotalStr = doubleToMoneyString(billTotalDbl);

            // Update text views with new info
            mTxtV_splitBill.setText(splitBillStr);
            mTxtV_tip.setText(tipStr);
            mTxtV_total.setText(billTotalStr);

        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}










