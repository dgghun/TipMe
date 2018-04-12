package com.dgg.tipme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class TipTotalsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button mBtn_splitMinus, mBtn_splitPlus, mBtn_tipPercentMinus, mBtn_tipPercentPlus, mBtn_rndUp, mBtn_rndDown, mBtn_Calc, mBtn_Home;
    private TextView  mTxtV_splitBill, mTxtV_splitCount, mTxtV_tipPercent, mTxtV_tip, mTxtV_total;
    private TextView mTxtV_totalPerHuman;

    private final int MAX_SPLIT_NUM = 99;
    private final int MIN_SPLIT_NUM = 1;
    private final Double MAX_PERCENT_TIP = 0.99;
    private final Double MIN_PERCENT_TIP = 0.00;
    private final String BILL_MAX = "$999,999.99";
    private final String BILL_MIN = "$1.00";
    private final String TOTAL = "Total";
    private final String YOU_PAY = "You Pay";
    private final String TOTAL_PER_HUMAN = "Each Human Pays";


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
        mTxtV_splitBill.setText(MainActivity.Users_Bill);
        mTxtV_tipPercent.setText(MainActivity.Users_Service);

        updateTipTotalAndSplitBill(percentStringToDouble(MainActivity.Users_Service), 1); //initial split is 1
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBtn_splitMinus.getId()) {
            SplitMinusBtnPressed();

        } else if (id == mBtn_splitPlus.getId()) {
            SplitPlusBtnPressed();

        } else if (id == mBtn_tipPercentMinus.getId()) {
            TipPercentMinusBtnPressed();

        } else if (id == mBtn_tipPercentPlus.getId()) {
            TipPercentPlusBtnPressed();

        } else if (id == mBtn_rndUp.getId()) {
            final Double ONE_DOLLAR = 1.00;
            RoundUpOrDownBtnPressed(ONE_DOLLAR);

        } else if (id == mBtn_rndDown.getId()) {
            final Double NEGATIVE_ONE_DOLLAR = -1.00;
            RoundUpOrDownBtnPressed(NEGATIVE_ONE_DOLLAR);

        } else if (id == mBtn_Calc.getId()) {
            Fragment fragment = new CalculatorFragment();
            ((MainActivity)getActivity()).replaceFragment(fragment, MainActivity.FRAG_TIP_TOTALS);

        } else if (id == mBtn_Home.getId()) {
            Fragment fragment = new MainFragment();
            ((MainActivity) getActivity()).replaceFragment(fragment, MainActivity.FRAG_TIP_TOTALS); // Start HowWasSvcFragment
        }
    }


    /**
     * Handles LongClick actions
     */
    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {


            if(mBtn_splitMinus.isPressed()){
                new MyTask().execute(mBtn_splitMinus);  // Pass in button pressed
            }
            else if(mBtn_splitPlus.isPressed()){
                new MyTask().execute(mBtn_splitPlus);   // Pass in button pressed
            }
            else if(mBtn_tipPercentMinus.isPressed()){
                new MyTask().execute(mBtn_tipPercentMinus); // Pass in button pressed
            }
            else if(mBtn_tipPercentPlus.isPressed()){
                new MyTask().execute(mBtn_tipPercentPlus);  // Pass in button pressed
            }

            return true;
        }
    };



    private class MyTask extends AsyncTask<Button, Button, Button>{

        Boolean flag_keepGoing = true;
        Boolean error = false;
        String errorMsg = "";


        @Override
        protected Button doInBackground(Button... buttons) {

            try{
                    while(flag_keepGoing && buttons[0].isPressed()) {
                        Thread.sleep(75);
                        publishProgress(buttons);  //calls onProgressUpdate()
                    }

            } catch (Exception e){
                Log.e("ERROR", e.getMessage());
                error = true;
                errorMsg = e.getMessage();
            }
            return buttons[0];
        }

        //Runs on UI thread after publishProgress() is called in doInBackground()
        @Override
        protected void onProgressUpdate(Button... buttons) {
            super.onProgressUpdate(buttons);

            if(buttons[0].getId() == mBtn_splitMinus.getId())
                flag_keepGoing = SplitMinusBtnPressed();
            else if(buttons[0].getId() == mBtn_splitPlus.getId())
                flag_keepGoing = SplitPlusBtnPressed();
            else if(buttons[0].getId() == mBtn_tipPercentMinus.getId())
                flag_keepGoing = TipPercentMinusBtnPressed();
            else if(buttons[0].getId() == mBtn_tipPercentPlus.getId())
                flag_keepGoing = TipPercentPlusBtnPressed();
        }

        // Runs on UI thread when doInBackground() is done.
        @Override
        protected void onPostExecute(Button button) {
            super.onPostExecute(button);

            if(error) Toast.makeText(view.getContext(), "ERROR:" + errorMsg, Toast.LENGTH_SHORT).show();

        }
    }



    //*******************************************************************
    //**** METHODS *****************************************************
    //********************************************************************

    private void RoundUpOrDownBtnPressed(Double dollarAmount){
        if(canRoundTipUpOrDown(dollarAmount)) {
            Double splitBill = moneyStringToDouble(mTxtV_splitBill.getText().toString());
            Double newTip = getRoundedTip(dollarAmount);
            mTxtV_tip.setText(doubleToMoneyString(newTip));
            mTxtV_total.setText(doubleToMoneyString(splitBill + newTip));
        }
    }


    private Boolean SplitMinusBtnPressed(){
        // if able to decrement, update bill info with decremented split count
        int splitCount = Integer.parseInt(mTxtV_splitCount.getText().toString());
        if(canDecrementSplitCount(splitCount)) {
            String newSplitCountStr = Integer.toString(splitCount - 1);
            mTxtV_splitCount.setText(newSplitCountStr);
            updateTipTotalAndSplitBill(percentStringToDouble(mTxtV_tipPercent.getText().toString()), splitCount - 1);

            return true;    //able to decrement
        }
        else return false;  //not able to decrement
    }


    private Boolean SplitPlusBtnPressed(){
        // Get incremented split count
        int newSplitCount = Integer.parseInt(mTxtV_splitCount.getText().toString()) + 1;

        // Check if you can increment split count,if so, update split count and bill info.
        if (canIncrementSplitCount(newSplitCount)) {
            String newSplitCountStr = Integer.toString(newSplitCount);
            mTxtV_splitCount.setText(newSplitCountStr);
            updateTipTotalAndSplitBill(percentStringToDouble(mTxtV_tipPercent.getText().toString()), newSplitCount);

            return true;    //can increment
        }
        else return false;  //cant increment
    }


    private Boolean TipPercentMinusBtnPressed(){
        Double newTipPercent = percentStringToDouble(mTxtV_tipPercent.getText().toString()) - 0.01; // Get decremented percent

        //check if can decrement percent
        if(canIncrementOrDecrementTipPercent(newTipPercent)){
            String newTipPercentStr = doubleToPercentString(newTipPercent);
            mTxtV_tipPercent.setText(newTipPercentStr);
            updateTipTotalAndSplitBill(newTipPercent, Integer.parseInt(mTxtV_splitCount.getText().toString()));

            return true;    // can increment
        }
        else return false;  //cant increment
    }


    private Boolean TipPercentPlusBtnPressed(){
        Double newTipPercent = percentStringToDouble(mTxtV_tipPercent.getText().toString()) + 0.01; // Get decremented percent

        //check if can decrement percent
        if(canIncrementOrDecrementTipPercent(newTipPercent)){
            String newTipPercentStr = doubleToPercentString(newTipPercent);
            mTxtV_tipPercent.setText(newTipPercentStr);
            updateTipTotalAndSplitBill(newTipPercent, Integer.parseInt(mTxtV_splitCount.getText().toString()));

            return true;    //can increment
        }
        else return false;  //cant increment
    }


    private void setUpButtons_TxtViews() {
        mBtn_splitMinus = (Button) view.findViewById(R.id.button_minus_SplitTip);
        mBtn_splitPlus = (Button) view.findViewById(R.id.button_plus_SplitTip);
        mBtn_tipPercentMinus = (Button) view.findViewById(R.id.button_minus_TipPercent);
        mBtn_tipPercentPlus = (Button) view.findViewById(R.id.button_plus_TipPercent);
        mBtn_rndDown = (Button) view.findViewById(R.id.button_Totals_roundDown);
        mBtn_rndUp = (Button) view.findViewById(R.id.button_Totals_roundUp);
        mBtn_Calc = (Button) view.findViewById(R.id.button_Totals_Calculator);
        mBtn_Home = (Button) view.findViewById(R.id.button_Totals_Home);

        mBtn_splitMinus.setOnClickListener(this);
        mBtn_splitPlus.setOnClickListener(this);
        mBtn_tipPercentMinus.setOnClickListener(this);
        mBtn_tipPercentPlus.setOnClickListener(this);
        mBtn_rndDown.setOnClickListener(this);
        mBtn_rndUp.setOnClickListener(this);
        mBtn_Calc.setOnClickListener(this);
        mBtn_Home.setOnClickListener(this);

        mBtn_splitMinus.setOnLongClickListener(longClickListener);
        mBtn_splitPlus.setOnLongClickListener(longClickListener);
        mBtn_tipPercentMinus.setOnLongClickListener(longClickListener);
        mBtn_tipPercentPlus.setOnLongClickListener(longClickListener);

        mTxtV_splitBill = (TextView) view.findViewById(R.id.textView_SplitBill_Digits);
        mTxtV_splitCount = (TextView) view.findViewById(R.id.textView_Split_Digit);
        mTxtV_tipPercent = (TextView) view.findViewById(R.id.textView_TipPecent_Digit);
        mTxtV_tip = (TextView) view.findViewById(R.id.textView_Tip_Digits);
        mTxtV_total = (TextView) view.findViewById(R.id.textView_Total_Digits);

        //Set up type writer and type face for Total Per Human text
        mTxtV_totalPerHuman = (TextView)view.findViewById(R.id.textview_TotalPerHuman);
    }


    private Double percentStringToDouble(String percentStr){

        return Double.parseDouble(percentStr.substring(0, percentStr.indexOf('%'))) / 100;
    }


    private Double moneyStringToDouble(String moneyStr){

        return Double.parseDouble(removeMoneyAndCommaChars(moneyStr));
    }


    private String doubleToMoneyString(Double doubleNum){

        return "$" + DECIMAL_FORMATTER.format(doubleNum);
    }


    private String doubleToPercentString(Double doubleNum){
        DecimalFormat decimalFormat = new DecimalFormat("00.00");
        String temp = decimalFormat.format(doubleNum * 100);

        if(doubleNum * 100 >= 0 && doubleNum * 100 < 10)
            return temp.substring(1, temp.indexOf('.')) + "%";
        return temp.substring(0, temp.indexOf('.')) + "%";
    }


    private String removeMoneyAndCommaChars(String str) {

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


    private Boolean canIncrementSplitCount(int splitCount){

        String error_splitRange = "Split range is " + MIN_SPLIT_NUM + " to " + MAX_SPLIT_NUM + ".";

        // Check if current split digit is at max
        if (splitCount > MAX_SPLIT_NUM) {
            Toast.makeText(view.getContext(), error_splitRange, Toast.LENGTH_SHORT).show();
            return false;   // err, split digit at max
        }

        // Check if divided bill doesn't cross minimum amount
        if( divideBill(splitCount, moneyStringToDouble(MainActivity.Users_Bill)) <  moneyStringToDouble(BILL_MIN)) {
            Toast.makeText(view.getContext(), "Can not split any lower.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private Boolean canDecrementSplitCount(int splitCount){
        if(splitCount <= MIN_SPLIT_NUM){
            Toast.makeText(view.getContext(), "Split range is " + MIN_SPLIT_NUM + " to " + MAX_SPLIT_NUM + ".", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private Boolean canIncrementOrDecrementTipPercent(Double newTipPercent){
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


    private Boolean canRoundTipUpOrDown(Double numToRoundBy){
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


    private Double getRoundedTip(Double numToRoundBy){
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


    private Double divideBill(int divisor, Double bill){

        return bill / (double) divisor;
    }


    private void updateTipTotalAndSplitBill(Double tipPercent, int splitCount){

        try {
            // Calculate splitBill, tip and total amounts
            Double bill = moneyStringToDouble(MainActivity.Users_Bill); // Get bill
            Double splitBill = bill / (double) splitCount;  //Get how much split bill is (bill per person)
            Double tipDbl;

            if(tipPercent.equals(MIN_PERCENT_TIP))
                tipDbl = MIN_PERCENT_TIP;           // If tip is 0%, no tip.
            else
                tipDbl  =  splitBill * tipPercent;         //Get how much tip is of split bill (per person)

            Double billTotalDbl = splitBill + tipDbl;    //Get how much total is per person


            //Convert doubles to strings with dollar signs
            String splitBillStr = doubleToMoneyString(splitBill);
            String tipStr = doubleToMoneyString(tipDbl);
            String billTotalStr = doubleToMoneyString(billTotalDbl);

            // Update text views with new info
            mTxtV_splitBill.setText(splitBillStr);
            mTxtV_tip.setText(tipStr);
            mTxtV_total.setText(billTotalStr);

            //Check if Tip + Bill = Total. If not fix this.
            //Had issues with rounding properly with 0.99 cents
            splitBillStr = mTxtV_splitBill.getText().toString();
            tipStr = mTxtV_tip.getText().toString();
            billTotalStr = mTxtV_total.getText().toString();
            if(moneyStringToDouble(splitBillStr) + moneyStringToDouble(tipStr) != moneyStringToDouble(billTotalStr))
                mTxtV_total.setText(doubleToMoneyString(moneyStringToDouble(splitBillStr) + moneyStringToDouble(tipStr)));

            //Check how split count and set Totals text view accordingly
            if(Integer.parseInt(mTxtV_splitCount.getText().toString()) > 1) {
                if(!mTxtV_totalPerHuman.getText().toString().equals(TOTAL_PER_HUMAN))
                    mTxtV_totalPerHuman.setText(TOTAL_PER_HUMAN);
            }
            else {
                if(!mTxtV_totalPerHuman.equals(YOU_PAY))
                    mTxtV_totalPerHuman.setText(YOU_PAY);
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("ERROR", e.getLocalizedMessage());
        }
    }

}










