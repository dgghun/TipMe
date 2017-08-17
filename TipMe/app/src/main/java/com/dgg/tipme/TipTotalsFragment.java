package com.dgg.tipme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by David_Garcia on 8/15/2017.
 */

public class TipTotalsFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button mBtn_splitMinus, mBtn_splitPlus, mBtn_tipMinus, mBtn_tipPlus, mBtn_rndUp, mBtn_rndDown, mBtn_Calc, mBtn_Home, mBtn_Quit;
    private TextView mTxtV_Bill, mTxtV_splitBill, mTxtV_splitNum, mTxtV_tipPercent, mTxtV_tip, mTxtV_total;

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
    public void onClick(View v) {
        int id = v.getId();

        if(id == mBtn_splitMinus.getId()){

        }
        else if(id == mBtn_splitPlus.getId()){

        }
        else if(id == mBtn_tipMinus.getId()){

        }
        else if(id == mBtn_tipPlus.getId()){

        }
        else if(id == mBtn_rndUp.getId()){

        }
        else if(id == mBtn_rndDown.getId()){

        }
        else if(id == mBtn_Calc.getId()){

        }
        else if(id ==mBtn_Home.getId()){

        }
        else if(id == mBtn_Quit.getId()){

        }
    }


    //**** METHODS ****//

    private void setUpButtons_TxtViews(){
        mBtn_splitMinus = (Button)view.findViewById(R.id.button_minus_SplitTip);
        mBtn_splitPlus = (Button)view.findViewById(R.id.button_plus_SplitTip);
        mBtn_tipMinus = (Button)view.findViewById(R.id.button_minus_TipPercent);
        mBtn_tipPlus = (Button)view.findViewById(R.id.button_plus_TipPercent);
        mBtn_rndDown = (Button)view.findViewById(R.id.button_Totals_roundDown);
        mBtn_rndUp = (Button)view.findViewById(R.id.button_Totals_roundUp);
        mBtn_Calc = (Button)view.findViewById(R.id.button_Totals_Calculator);
        mBtn_Home = (Button)view.findViewById(R.id.button_Totals_Home);
        mBtn_Quit = (Button)view.findViewById(R.id.button_Totals_Quit);

        mBtn_splitMinus.setOnClickListener(this);
        mBtn_splitPlus.setOnClickListener(this);
        mBtn_tipMinus.setOnClickListener(this);
        mBtn_tipPlus.setOnClickListener(this);
        mBtn_rndDown.setOnClickListener(this);
        mBtn_rndUp.setOnClickListener(this);
        mBtn_Calc.setOnClickListener(this);
        mBtn_Home.setOnClickListener(this);
        mBtn_Quit.setOnClickListener(this);

        mTxtV_Bill = (TextView)view.findViewById(R.id.textView_Bill_Digits);
        mTxtV_splitBill = (TextView)view.findViewById(R.id.textView_SplitBill_Digits);
        mTxtV_splitNum = (TextView)view.findViewById(R.id.textView_Split_Digit);
        mTxtV_tipPercent = (TextView)view.findViewById(R.id.textView_TipPecent_Digit);
        mTxtV_tip = (TextView)view.findViewById(R.id.textView_Tip_Digits);
        mTxtV_total = (TextView)view.findViewById(R.id.textView_Total_Digits);

    }
}
