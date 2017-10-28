package com.dgg.tipme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Stack;

/**
 * Created by David_Garcia on 8/15/2017.
 */

public class TipTotalsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button mBtn_splitMinus, mBtn_splitPlus, mBtn_tipMinus, mBtn_tipPlus, mBtn_rndUp, mBtn_rndDown, mBtn_Calc, mBtn_Home, mBtn_Quit;
    private TextView mTxtV_Bill, mTxtV_splitBill, mTxtV_splitNum, mTxtV_tipPercent, mTxtV_tip, mTxtV_total;

    private final int MAX_SPLIT_NUM = 100;
    private final int MIN_SPLIT_NUM = 1;
    private final int MAX_PERCENT_TIP = 10;
    private final int MIN_PERCENT_TIP = 0;
    private final String ERR_TIP_LOWER_THAN_ZERO = "err_tipLowerThanZero";

    private final DecimalFormat decimalFormatter = new DecimalFormat("###,###,##0.00");

    private Stack<String> mStackOfBills;
    private Stack<BillInfo> mStackOfBillInfo;

    // FOR TESTING
    String BILL_MAX = "$999,999.99";
    String BILL_MIN = "$1.00";
    String BILL_TEST = "$5.11";
    String TIP_PERCENT_TEST = "15%";

    /**
     * Class BillInfo - holds bill info. Used in stack
     */
    class BillInfo{
        Double splitBillDbl, tipDbl, totalDbl;
        String splitBillStr, tipStr, totalStr;

        public BillInfo(){}

        public Double getSplitBillDbl() {
            return splitBillDbl;
        }

        public void setSplitBillDbl(Double splitBillDbl) {
            this.splitBillDbl = splitBillDbl;
        }

        public Double getTipDbl() {
            return tipDbl;
        }

        public void setTipDbl(Double tipDbl) {
            this.tipDbl = tipDbl;
        }

        public Double getTotalDbl() {
            return totalDbl;
        }

        public void setTotalDbl(Double totalDbl) {
            this.totalDbl = totalDbl;
        }

        public String getSplitBillStr() {
            return splitBillStr;
        }

        public void setSplitBillStr(String splitBillStr) {
            this.splitBillStr = splitBillStr;
        }

        public String getTipStr() {
            return tipStr;
        }

        public void setTipStr(String tipStr) {
            this.tipStr = tipStr;
        }

        public String getTotalStr() {
            return totalStr;
        }

        public void setTotalStr(String totalStr) {
            this.totalStr = totalStr;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.fragment_tip_totals, container, false);

        setUpButtons_TxtViews();

        //Set up array of bill calculations
        mStackOfBills = new Stack<>();
        mStackOfBillInfo = new Stack<>();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Testing...will change to serialized info from previous fragment.
        mTxtV_Bill.setText(BILL_TEST);
        mTxtV_splitBill.setText(BILL_TEST);
        mTxtV_tipPercent.setText(TIP_PERCENT_TEST);

        Double tipPercentDbl = Double.parseDouble(TIP_PERCENT_TEST.substring(0, TIP_PERCENT_TEST.indexOf('%'))) / 100;
        Double splitBillDbl = Double.parseDouble(removeMoneyAndCommaChars(BILL_TEST));
        updateTip(tipPercentDbl,splitBillDbl);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBtn_splitMinus.getId()) {
            // if able to decrement, pop old bill and set bill with it
            if (decrementSplitDigitAndUpdate() > 0) {
                String temp = mStackOfBills.pop();
                mTxtV_splitBill.setText(temp);
                Toast.makeText(view.getContext(), "Popped: " + temp, Toast.LENGTH_SHORT).show();
            }

        } else if (id == mBtn_splitPlus.getId()) {
            String splitBillToSave = mTxtV_splitBill.getText().toString(); // get current SplitBill amount before updating

            // If able to increment and update, push "splitBillToSave"  onto stack
           if(incrementSplitDigitAndUpdate()) {

               mStackOfBills.push(splitBillToSave);
               Toast.makeText(view.getContext(), "Pushed: " + splitBillToSave, Toast.LENGTH_SHORT).show();
           }

        } else if (id == mBtn_tipMinus.getId()) {

        } else if (id == mBtn_tipPlus.getId()) {

        } else if (id == mBtn_rndUp.getId()) {

        } else if (id == mBtn_rndDown.getId()) {

        } else if (id == mBtn_Calc.getId()) {

        } else if (id == mBtn_Home.getId()) {

        } else if (id == mBtn_Quit.getId()) {

            getActivity().finish();
        }
    }


    //**** METHODS ****//

    /**
     * setUpButtons_TxtViews()
     */
    private void setUpButtons_TxtViews() {
        mBtn_splitMinus = (Button) view.findViewById(R.id.button_minus_SplitTip);
        mBtn_splitPlus = (Button) view.findViewById(R.id.button_plus_SplitTip);
        mBtn_tipMinus = (Button) view.findViewById(R.id.button_minus_TipPercent);
        mBtn_tipPlus = (Button) view.findViewById(R.id.button_plus_TipPercent);
        mBtn_rndDown = (Button) view.findViewById(R.id.button_Totals_roundDown);
        mBtn_rndUp = (Button) view.findViewById(R.id.button_Totals_roundUp);
        mBtn_Calc = (Button) view.findViewById(R.id.button_Totals_Calculator);
        mBtn_Home = (Button) view.findViewById(R.id.button_Totals_Home);
        mBtn_Quit = (Button) view.findViewById(R.id.button_Totals_Quit);

        mBtn_splitMinus.setOnClickListener(this);
        mBtn_splitPlus.setOnClickListener(this);
        mBtn_tipMinus.setOnClickListener(this);
        mBtn_tipPlus.setOnClickListener(this);
        mBtn_rndDown.setOnClickListener(this);
        mBtn_rndUp.setOnClickListener(this);
        mBtn_Calc.setOnClickListener(this);
        mBtn_Home.setOnClickListener(this);
        mBtn_Quit.setOnClickListener(this);

        mTxtV_Bill = (TextView) view.findViewById(R.id.textView_Bill_Digits);
        mTxtV_splitBill = (TextView) view.findViewById(R.id.textView_SplitBill_Digits);
        mTxtV_splitNum = (TextView) view.findViewById(R.id.textView_Split_Digit);
        mTxtV_tipPercent = (TextView) view.findViewById(R.id.textView_TipPecent_Digit);
        mTxtV_tip = (TextView) view.findViewById(R.id.textView_Tip_Digits);
        mTxtV_total = (TextView) view.findViewById(R.id.textView_Total_Digits);

    }


    /**
     * reamoveMoneyAndCommaChars(String)
     *
     * @param str
     * @return
     */
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


    /**
     * incrementSplitDigitAndUpdate()
     *
     * @return returns new incremented split digit integer
     */
    public boolean incrementSplitDigitAndUpdate() {
        int currentSplitNum = Integer.parseInt(mTxtV_splitNum.getText().toString());    // get the current split digit
        String error_splitRange = "Split range is " + MIN_SPLIT_NUM + " to " + MAX_SPLIT_NUM + ".";

        // Check if current split digit is at max
        if (currentSplitNum >= MAX_SPLIT_NUM) {
            Toast.makeText(view.getContext(), error_splitRange, Toast.LENGTH_SHORT).show();
            return false;   // err, split digit at max
        }
        else {   // Current split digit is okay so increment
            int newSpiltDigitInt = currentSplitNum + 1;                     // save incremented current split digit to variable
            Double billDbl = divideBill(newSpiltDigitInt);  //divides bill and returns if no errs

            if(billDbl <= 0.00) {   //if err..
                Toast.makeText(view.getContext(), "Bill can't be split any lower.", Toast.LENGTH_SHORT).show();
                return false;   // err, bill cant be split any lower
            }
            else {
                //TODO Probably need to push onto stack here instead of after this method
                String newSplitDigitStr = Integer.toString(newSpiltDigitInt);   // Save as string
                String billDblStr = "$" + decimalFormatter.format(billDbl);   // Save as string
                mTxtV_splitNum.setText(newSplitDigitStr);                       //set new split digit
                mTxtV_splitBill.setText(billDblStr);   //Set new split bill amount
                return true;    // no errs
            }
        }
    }


    /**
     * @return returns if successful in decrementing split digit
     */
    public int decrementSplitDigitAndUpdate() {
        int currentSplitNum = Integer.parseInt(mTxtV_splitNum.getText().toString());    // get the current split digit
        String error_splitRange = "Split range is " + MIN_SPLIT_NUM + " to " + MAX_SPLIT_NUM + ".";

        // check if current split num is in range. If so, decrement.
        if (currentSplitNum <= MIN_SPLIT_NUM) {
            Toast.makeText(view.getContext(), error_splitRange, Toast.LENGTH_SHORT).show();
            return -1;      //return unsuccessful
        } else {
            int newSpiltDigitInt = currentSplitNum - 1;                     // decrement split digit
            String newSplitDigitStr = Integer.toString(newSpiltDigitInt);   // Save as string
            mTxtV_splitNum.setText(newSplitDigitStr);                       //set new split digit
            return 1;                                                       // return successful
        }
    }


    /**
     * divideBill()
     *
     * @param divisor
     */
    public Double divideBill(int divisor) {
        String strBill = mTxtV_Bill.getText().toString();                        // get the  bill text
        Double bill = Double.parseDouble(removeMoneyAndCommaChars(strBill));     // parse bill str to number
        bill = bill / divisor;                                                   // divide the bill

        if(bill < 1.00)
            return 0.00;
        else {
            return bill;
        }
    }

    /**
     *
     */
    public void updateTip(Double tipPercent, Double bill){

        //TODO working on this
        try {
            Double tipDbl =  bill * tipPercent;
            Double newBillTotalDbl = bill + tipDbl;
            String tipStr = "$" + decimalFormatter.format(tipDbl);
            String billTotalStr = "$" + decimalFormatter.format(newBillTotalDbl);

            mTxtV_tip.setText(tipStr);
            mTxtV_total.setText(billTotalStr);
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     *
     */
    public void updateTotal(){

    }

}










