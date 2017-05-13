package com.dgg.tipme;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;
import com.dgg.tipme.R;

/**
 * Created by David_Garcia on 5/11/2017.
 */

public class MainFragment extends Fragment {

    private View view;
    private Button Btn_Tip;
    private Button Btn_ItemizedTip;
    private Button Btn_Calculator;
    private TextView TxtView_MainHeader;




    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){
        view = inflater.inflate(R.layout.fragment_main, container, false);


        setUpButtons();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        setUpHeader(); // Animates header
    }


    /*** METHODS *******************************************************************************************************/

    /** setUpButtons()
     *
     */
    public void setUpButtons(){

        // Get handle on buttons
        Btn_Tip = (Button)view.findViewById(R.id.button_Tip);
        Btn_Calculator = (Button)view.findViewById(R.id.button_Calculator);
        Btn_ItemizedTip = (Button) view.findViewById(R.id.button_ItemizedTip);

    }


    /** setUpHeader()
     *
     */
    public void setUpHeader(){
        TxtView_MainHeader = (TextView)view.findViewById(R.id.TextView_mainScreenHeader);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DS-DIGIB.TTF");
        TxtView_MainHeader.setTypeface(typeface);

       //Add a character every few seconds
        Typewriter typewriter = (Typewriter) TxtView_MainHeader;
        typewriter.setCharacterDelay(50);
        typewriter.animateText(getResources().getString(R.string.str_textview_how_can_i_help));

         // Test Thread
//        Thread myThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    try{
//                        handlerAnimateText.sendMessage(handlerAnimateText.obtainMessage());
//                        Thread.sleep(2000);
//                    }catch (Throwable t){
//
//                    }
//                }
//            }
//        });
//        myThread.start();

    }

    /**
     * Runnable handler
     */
    Handler handlerAnimateText = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Toast.makeText(view.getContext(), msg.toString(), Toast.LENGTH_SHORT).show();
        }
    };


}
