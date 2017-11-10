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
import java.util.Stack;

/**
 * Created by David_Garcia on 8/15/2017.
 */

public class TipTotalsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button mBtn_splitMinus, mBtn_splitPlus, mBtn_tipPercentMinus, mBtn_tipPercentPlus, mBtn_rndUp, mBtn_rndDown, mBtn_Calc, mBtn_Home, mBtn_Quit;
    private TextView mTxtV_Bill, mTxtV_splitBill, mTxtV_splitNum, mTxtV_tipPercent, mTxtV_tip, mTxtV_total;

    private final int MAX_SPLIT_NUM = 100;
    private final int MIN_SPLIT_NUM = 1;
    private final int MAX_PERCENT_TIP = 10;
    private final int MIN_PERCENT_TIP = 0;
    private final String ERR_TIP_LOWER_THAN_ZERO = "err_tipLowerThanZero";

    private final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("###,###,##0.00");

    private Stack<String> mStackOfBills;
    private Stack<BillInfo> mStackOfBillInfo;

    // FOR TESTING
    String BILL_MAX = "$999,999.99";
    String BILL_MIN = "$1.00";
    String BILL_TEST = "$13.31";
    String TIP_PERCENT_TEST = "15%";

    /**
     * Class BillInfo - holds bill info. Used in stack
     */
    class BillInfo{
        String splitBill, tip, total;

        public BillInfo(){}

        public void setSplitBill(String splitBill) {
            this.splitBill = splitBill;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSplitBill() {
            return splitBill;
        }

        public String getTip() {
            return tip;
        }

        public String getTotal() {
            return total;
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

        // Set text views
        // Testing...will change to serialized info from previous fragment.
        mTxtV_Bill.setText(BILL_TEST);
        mTxtV_splitBill.setText(BILL_TEST);
        mTxtV_tipPercent.setText(TIP_PERCENT_TEST);

        updateTipAndTotal(percentStringToDouble(TIP_PERCENT_TEST),moneyStringToDouble(BILL_TEST));

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBtn_splitMinus.getId()) {
            // if able to decrement, pop old bill and set bill with it
            int splitCount = Integer.parseInt(mTxtV_splitNum.getText().toString());
            if(canDecrementSplitCount(splitCount))
                decrementSplitCountAndUpdate(splitCount);


        } else if (id == mBtn_splitPlus.getId()) {
            // Get current split count and split bill
            int newSplitCount = Integer.parseInt(mTxtV_splitNum.getText().toString()) + 1;
            Double billDbl = moneyStringToDouble(mTxtV_Bill.getText().toString());

            // Check if you can increment split count,if so,
            // update bill info and push current bill info onto stack.
            if (canIncrementSplitCount(newSplitCount, billDbl))
                incrementSplitCountAndUpdate(newSplitCount, billDbl);

        } else if (id == mBtn_tipPercentMinus.getId()) {

            // TODO Start working on this
        } else if (id == mBtn_tipPercentPlus.getId()) {

        } else if (id == mBtn_rndUp.getId()) {

        } else if (id == mBtn_rndDown.getId()) {

        } else if (id == mBtn_Calc.getId()) {

        } else if (id == mBtn_Home.getId()) {

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
        mTxtV_splitNum = (TextView) view.findViewById(R.id.textView_Split_Digit);
        mTxtV_tipPercent = (TextView) view.findViewById(R.id.textView_TipPecent_Digit);
        mTxtV_tip = (TextView) view.findViewById(R.id.textView_Tip_Digits);
        mTxtV_total = (TextView) view.findViewById(R.id.textView_Total_Digits);

    }



    public Double percentStringToDouble(String percentStr){

        return Double.parseDouble(TIP_PERCENT_TEST.substring(0, TIP_PERCENT_TEST.indexOf('%'))) / 100;
    }



    public Double moneyStringToDouble(String moneyStr){

        return Double.parseDouble(removeMoneyAndCommaChars(moneyStr));
    }



    public String doubleToMoneyString(Double doubleNum){

        return "$" + DECIMAL_FORMATTER.format(doubleNum);
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



    public Boolean canIncrementSplitCount(int splitCount, Double currentBill){

        String error_splitRange = "Split range is " + MIN_SPLIT_NUM + " to " + MAX_SPLIT_NUM + ".";

        // Check if current split digit is at max
        if (splitCount >= MAX_SPLIT_NUM) {
            Toast.makeText(view.getContext(), error_splitRange, Toast.LENGTH_SHORT).show();
            return false;   // err, split digit at max
        }

        // Check if divided bill doesn't cross minimum amount
        if( divideBill(splitCount, currentBill) <  moneyStringToDouble(BILL_MIN)) {
            Toast.makeText(view.getContext(), "Can not split any lower.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    public void incrementSplitCountAndUpdate(int newSplitCount, Double billDbl){
        Double newSplitBillDbl = divideBill(newSplitCount, billDbl);    //calculate new split bill
        String newSplitBillStr =  doubleToMoneyString(newSplitBillDbl);
        String newSplitCountStr = Integer.toString(newSplitCount);

        // Save current bill info and push to stack
        BillInfo currentBillInfo = new BillInfo();
        currentBillInfo.setSplitBill(mTxtV_splitBill.getText().toString());
        currentBillInfo.setTip(mTxtV_tip.getText().toString());
        currentBillInfo.setTotal(mTxtV_total.getText().toString());
        mStackOfBillInfo.push(currentBillInfo);

        // Update Text Views with new bill info
        mTxtV_splitNum.setText(newSplitCountStr);
        mTxtV_splitBill.setText(newSplitBillStr);
        updateTipAndTotal(percentStringToDouble(mTxtV_tipPercent.getText().toString()), newSplitBillDbl);
    }



    public Boolean canDecrementSplitCount(int splitCount){
        if(splitCount <= MIN_SPLIT_NUM){
            Toast.makeText(view.getContext(), "Split range is " + MIN_SPLIT_NUM + " to " + MAX_SPLIT_NUM + ".", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    public void decrementSplitCountAndUpdate(int splitCount){

        // decrement and update split count info
        String newSplitCountStr = Integer.toString(splitCount - 1);
        mTxtV_splitNum.setText(newSplitCountStr);

        //Set text views with previous bill info and pop off stack
        mTxtV_splitBill.setText(mStackOfBillInfo.peek().getSplitBill());
        mTxtV_tip.setText(mStackOfBillInfo.peek().getTip());
        mTxtV_total.setText(mStackOfBillInfo.peek().getTotal());
        mStackOfBillInfo.pop();
    }



    public Double divideBill(int divisor, Double bill){

        return bill / (double) divisor;
    }



    public void updateTipAndTotal(Double tipPercent, Double bill){

        try {
            Double tipDbl =  bill * tipPercent;
            Double newBillTotalDbl = bill + tipDbl;
            String tipStr = "$" + DECIMAL_FORMATTER.format(tipDbl);
            String billTotalStr = "$" + DECIMAL_FORMATTER.format(newBillTotalDbl);

            mTxtV_tip.setText(tipStr);
            mTxtV_total.setText(billTotalStr);
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}










