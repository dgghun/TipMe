package com.dgg.tipme;

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

/**
 * Created by David_Garcia on 8/15/2017.
 */

public class TipTotalsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button mBtn_splitMinus, mBtn_splitPlus, mBtn_tipMinus, mBtn_tipPlus, mBtn_rndUp, mBtn_rndDown, mBtn_Calc, mBtn_Home, mBtn_Quit;
    private TextView mTxtV_Bill, mTxtV_splitBill, mTxtV_splitNum, mTxtV_tipPercent, mTxtV_tip, mTxtV_total;

    private final int MAX_SPLIT_NUM = 100;
    private final int MIN_SPLIT_NUM = 1;
    private final int MAX_PERCENT_TIP = 99;
    private final int MIN_PERCENT_TIP = 0;

    private ArrayList<String> mArrayOfBill;

    // FOR TESTING
    String BILL_MAX = "$999,999.99";
    String BILL_MIN = "$1.00";
    String BILL_NORMAL = "$145.37";


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.fragment_tip_totals, container, false);

        setUpButtons_TxtViews();

        //Set up array of bill calculations
        mArrayOfBill = new ArrayList<String>();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //TEMP - DELETE THIS
        mTxtV_Bill.setText(BILL_MAX);
        mTxtV_splitBill.setText(BILL_MAX);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBtn_splitMinus.getId()) {
            //incrementSplitDigit()
            //divideAndUpdateCurrentBill(int spitDigit) // And add current bill to arrayOfBills
            //calculateNewTip()
            split_incrementOrDecrement(false, true);  //Decrement the number split by

        } else if (id == mBtn_splitPlus.getId()) {

            split_incrementOrDecrement(true, false);  //true is Increment the number split by

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
     * split_incrementOrDecrement(boolean increment, boolean decrement)
     * @param increment
     * @param decrement
     */
    public void split_incrementOrDecrement(boolean increment, boolean decrement) {

        //TODO - NEED TO FINISH THIS
        int currentSplitNum = Integer.parseInt(mTxtV_splitNum.getText().toString());    // get the current split digit
        String error_splitRange = "Split range is " + MIN_SPLIT_NUM + " to " + MAX_SPLIT_NUM + ".";

        // check if current split num is in range. If so, increment/decrement by passed in integer.
        if (currentSplitNum <= MIN_SPLIT_NUM && decrement)
            Toast.makeText(view.getContext(), error_splitRange, Toast.LENGTH_SHORT).show();
        else if (currentSplitNum >= MAX_SPLIT_NUM && increment)
            Toast.makeText(view.getContext(), error_splitRange, Toast.LENGTH_SHORT).show();
        else {

            int newSplitCount;

            //TODO - Working on this
            if(increment) {
                newSplitCount = currentSplitNum + 1;   //increment split count
                String strBill = mTxtV_splitBill.getText().toString(); // get the current bill text

                mArrayOfBill.add(strBill);  //Add current number to array for recalling when split is decremented
                Toast.makeText(view.getContext(), mArrayOfBill.toString(), Toast.LENGTH_SHORT).show();

                Double bill = Double.parseDouble(removeMoneyAndCommaChars(strBill)); //parse current Split bill amount to number
                bill = bill / newSplitCount;    //divide the split bill

                strBill = Double.toString(bill);    //convert new bill number to string
                strBill = "$" + strBill.substring(0, strBill.indexOf('.') + 3); // add $ and remove trailing digits
                mTxtV_splitBill.setText(strBill);   //set text splitView


                Toast.makeText(view.getContext(), Double.toString(bill), Toast.LENGTH_SHORT).show();
            }
            else { // decrement
                newSplitCount = currentSplitNum - 1;   // decrement split count
            }

            Double splitBillAmount = Double.parseDouble(removeMoneyAndCommaChars(mTxtV_splitBill.getText().toString()));    // get current split bill amount


            //TODO - Working on how to handle division long remainders.
                String newSplitDigit = Integer.toString(newSplitCount); // add current num by passed in integer. Save as string
                mTxtV_splitNum.setText(newSplitDigit);      //set new split by number
        }

    }


}









