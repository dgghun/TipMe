package com.dgg.tipme;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
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


    public Typewriter(Context context) {
        super(context);
    }

    public Typewriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            if(mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
            else
                mHandler.postDelayed(blinkingLastCharacter, mDelay);
        }
    };

    private Runnable blinkingLastCharacter = new Runnable() {
        @Override
        public void run() {
            if(getText().toString().equals(mText)){
                setText(mText.subSequence(0, mText.length()-1) + "_");
            }
            else{
                setText(mText);
            }
            mHandler.postDelayed(blinkingLastCharacter, mDelay * 10);
        }
    };

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }



    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

}
