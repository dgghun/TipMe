package com.dgg.tipme;


import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ButtonInputHandler {

    private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");
    private double mTotal = 0.00;
    private final int MAX_LENGTH = 8;


    public String append(String input, String appendTo){

        if(appendTo.length() >= MAX_LENGTH)
            return appendTo;
        else {
            mTotal = Double.parseDouble(appendTo);
            mTotal *= 10;                                       // Multiply by 10 to push decimal over.
            Double tempNum = Double.parseDouble(input) / 100;  // Divide by 100 to put input at end.
            mTotal += tempNum;

            return mDecimalFormat.format(mTotal);
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
