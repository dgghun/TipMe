package com.dgg.tipme;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ButtonInputHandler {

    private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");
    private double mTotal = 0.00;
    private View mView;
    public final int MAX_LENGTH = 9;


    public ButtonInputHandler(View view) {
        this.mView = view;
    }

    public String append(String input, String appendTo){

        if(appendTo.length() >= MAX_LENGTH) { // If hits max digit length do nothing.

            Toast.makeText(mView.getContext(), "Max digits reached.", Toast.LENGTH_SHORT).show();
            return appendTo;
        }
        else {
            mTotal = Double.parseDouble(appendTo);
            mTotal *= 10;                                       // Multiply total by 10 to push decimal over.
            Double tempNum = Double.parseDouble(input) / 100;   // Divide input by 100 to put input at end.

            return mDecimalFormat.format(mTotal + tempNum);
        }
    }

    public String delete(String input){

        if(input.equals("0.00"))
            return input;
        else{
            mDecimalFormat.setRoundingMode(RoundingMode.DOWN);
            return mDecimalFormat.format(Double.parseDouble(input) / 10);
        }
    }

}
