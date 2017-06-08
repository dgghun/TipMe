package com.dgg.tipme;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

/**
 * Adapted from:
 * http://stackoverflow.com/questions/6700374/android-character-by-character-display-text-animation
 */

/**
 * This class is used to make a character by charcter "typewriter" effect for a TextViews text.
 */
public class Typewriter extends AppCompatTextView {

    private CharSequence mText;
    private int mIndex;
    private long mDelay = 500; //Default 500ms delay
    private boolean mFlag_AnimateLastChar;



    public Typewriter(Context context) {
        super(context);
    }

    public Typewriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler mHandler = new Handler();   //Thread handler

    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {

            //Recursive loop to print characters one by one
            setText(mText.subSequence(0, mIndex++));
            if(mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
            else {
                mHandler.postDelayed(blinkingLastCharacter, mDelay * 10); // make last character blink
            }
        }
    };

    private Runnable blinkingLastCharacter = new Runnable() {
        @Override
        public void run() {

            //Do this until flag is set to stop
            if (getText().toString().equals(mText)) {
                String text = mText.subSequence(0, mText.length() - 1) + "_";
                setText(text);
            } else {
                setText(mText);
            }
             mHandler.postDelayed(blinkingLastCharacter, mDelay * 10);

        }
    };

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;     // index for loop to print out characters

        setText("");
        mHandler.removeCallbacks(blinkingLastCharacter);
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }




    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

}
