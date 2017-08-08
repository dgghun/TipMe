package com.dgg.tipme;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class TipAmountFragment extends Fragment {

    private View view;
    private Typewriter writer_header;   //Custom class to animate textView text like a "type writer"
    private ImageView mImgView_badHead;
    private ImageView mImgView_goodHead;
    private ImageView mImgView_greatHead;
    private AnimationDrawable mAnim_badHead;
    private AnimationDrawable mAnim_goodHead;
    private AnimationDrawable mAnim_greatHead;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        view = inflater.inflate(R.layout.fragment_tip_amount, container, false);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

    }

}
