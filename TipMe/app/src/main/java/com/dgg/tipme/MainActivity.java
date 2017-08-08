package com.dgg.tipme;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

public class MainActivity extends SingleFragmentActivity {

    public final static String FRAG_MAIN_FRAG = "frag_main";
    public final static String FRAG_BILL_AMOUNT = "frag_bill_amount";
    public final static String FRAG_TIP_AMOUNT = "frag_tip_amount";

    public static String FRAG_LAST = null;


    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    //Handles back press navigation flags. Was having issues w/ onclick working when
    //using the fragment stack from TipAmountFrag to BillAmountFrag. Using these
    //flags for now until I figure the issue out.
    @Override
    public void onBackPressed(){

        if(FRAG_LAST.equals(null))  // Close app
            super.onBackPressed();
        else if(FRAG_LAST.equals(FRAG_MAIN_FRAG)) {
            FRAG_LAST = null;       //set last to end app
            replaceFragment(new MainFragment());
        }
        else if(FRAG_LAST.equals(FRAG_BILL_AMOUNT)) {
            FRAG_LAST = FRAG_MAIN_FRAG;     //set last to mainFragment
            replaceFragment(new BillAmountFragment());
        }
        else super.onBackPressed();
    }


    /** METHODS **/


    /** replaceFragment(Fragment)
     *
     * @param someFragment
     */
    public void replaceFragment(Fragment someFragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // fragment animation
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.commit();
    }

    /** replaceFragment(Fragment, String)
     *
     * @param someFragment
     */
    public void replaceFragment(Fragment someFragment, String currentFrag){

        FRAG_LAST = currentFrag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // fragment animation
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.commit();
    }

}
