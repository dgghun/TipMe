package com.dgg.tipme;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CalculatorFragment extends Fragment implements View.OnClickListener  {

    private View view;
    private Typeface typeface;
    private TextView TxtV_inputDisplay, TxtV_dollarSign;
    private final int BTN_DEL = 10, BTN_EQUALS = 11, BTN_TIMES = 12, BTN_DIVIDE = 13, BTN_PLUS = 14, BTN_MINUS = 15;
    private List<Integer> buttonIds;
    private  List<Button> buttons;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        view = inflater.inflate(R.layout.fragment_calculator, container, false);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DS-DIGIB.TTF");  //get custom typeface from assets

        TxtV_inputDisplay = (TextView)view.findViewById(R.id.EditText_calculatorInputDisplay);
        TxtV_inputDisplay.setTypeface(typeface);
        TxtV_dollarSign = (TextView) view.findViewById(R.id.TextView_calculatorInputArea_DollarSign);
        TxtV_dollarSign.setTypeface(typeface);

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

//        //Search for button id pressed
//        for (int i = 0; i <= buttonIds.size(); i++) {
//
//            if (id == buttonIds.get(i)) {   //If id found, i is the button pressed (0-9, X or $)
//
//                if (i == BTN_DEL)
//                    mEditTxt_billInput.setText(inputHandler.delete(mEditTxt_billInput.getText().toString()));
//                else if (i == BTN_EQUALS) {
//                    String currentInput = mEditTxt_billInput.getText().toString();
//
//                    if(currentInput.equals("0.00"))
//                        Toast.makeText(view.getContext(), "Enter bill amount to continue.", Toast.LENGTH_SHORT).show();
//                    else if(Double.parseDouble(currentInput) < 1.0)
//                        Toast.makeText(view.getContext(), "Bill must be $1.00 or more.", Toast.LENGTH_SHORT).show();
//                    else {
//                        MainActivity.Users_Bill = mEditTxt_billInput.getText().toString();  // Set users bill in activity to access in TipTotalsFragment
//                        Fragment fragment = new HowWasSvcFragment();
//                        ((MainActivity) getActivity()).replaceFragment(fragment, MainActivity.FRAG_BILL_AMOUNT); // Start HowWasSvcFragment
//                    }
//
//                } else    // i is a number so append it to current textView string
//                    mEditTxt_billInput.setText(inputHandler.append(Integer.toString(i), mEditTxt_billInput.getText().toString()));
//
//                i = buttonIds.size() + 1; // break from loop
//            }
//        }
    }


    /**
     * setUpButtons
     */
    public void setUpButtons() {
        buttonIds = new ArrayList<>();
        buttons = new ArrayList<>();


        int mLastBtn = 15;
        // Set button ids, button views and click listener
        for (int i = 0; i <= mLastBtn; i++) {
            if (i == BTN_DEL) {
                buttonIds.add(getResources().getIdentifier("button_calculatorCalcNumber_delete", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(BTN_DEL)));
                buttons.get(BTN_DEL).setOnClickListener(this);
                buttons.get(BTN_DEL).setTypeface(typeface);
            }
            else if (i == BTN_EQUALS) {
                buttonIds.add(getResources().getIdentifier("button_calculatorCalcNumber_equals", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(BTN_EQUALS)));
                buttons.get(BTN_EQUALS).setOnClickListener(this);
                buttons.get(BTN_EQUALS).setTypeface(typeface);
            }
            else if (i == BTN_PLUS){
                buttonIds.add(getResources().getIdentifier("button_calculatorCalcNumber_plus", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(BTN_PLUS)));
                buttons.get(BTN_PLUS).setOnClickListener(this);
                buttons.get(BTN_PLUS).setTypeface(typeface);
            }
            else if (i == BTN_MINUS){
                buttonIds.add(getResources().getIdentifier("button_calculatorCalcNumber_minus", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(BTN_MINUS)));
                buttons.get(BTN_MINUS).setOnClickListener(this);
                buttons.get(BTN_MINUS).setTypeface(typeface);
            }
            else if (i == BTN_TIMES){
                buttonIds.add(getResources().getIdentifier("button_calculatorCalcNumber_times", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(BTN_TIMES)));
                buttons.get(BTN_TIMES).setOnClickListener(this);
                buttons.get(BTN_TIMES).setTypeface(typeface);
            }
            else if (i == BTN_DIVIDE){
                buttonIds.add(getResources().getIdentifier("button_calculatorCalcNumber_divide", "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(BTN_DIVIDE)));
                buttons.get(BTN_DIVIDE).setOnClickListener(this);
                buttons.get(BTN_DIVIDE).setTypeface(typeface);
            }
            else {
                buttonIds.add(getResources().getIdentifier("button_calculatorCalcNumber_" + i, "id", getActivity().getPackageName()));
                buttons.add((Button) view.findViewById(buttonIds.get(i)));
                buttons.get(i).setOnClickListener(this);
                buttons.get(i).setTypeface(typeface);
            }
        }
    }   //END of setUpButtons()
}
