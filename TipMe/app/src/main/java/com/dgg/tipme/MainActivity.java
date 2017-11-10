package com.dgg.tipme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends SingleFragmentActivity {

    public final static String FRAG_MAIN_FRAG = "frag_main";
    public final static String FRAG_BILL_AMOUNT = "frag_bill_amount";
    public final static String FRAG_HOW_WAS_SVC = "frag_tip_amount";
    public final static String FRAG_TIP_TOTALS = "frag_tip_totals";

    private static String FRAG_LAST = "";
    private final String END = "end";


    @Override
    protected Fragment createFragment() {
//        return new MainFragment();
        return new TipTotalsFragment(); // TESTING!!!!!!!!!!!!!!!! Delete this when done!!
    }

    //Handles back press navigation flags. Was having issues w/ onclick working when
    //using the fragment stack from TipAmountFrag to BillAmountFrag. Using these
    //flags for now until I figure the issue out.
    @Override
    public void onBackPressed(){

        if(FRAG_LAST.equals(END))  // // Back pressed from MainFrag, Close app
            super.onBackPressed();
        else if(FRAG_LAST.equals(FRAG_MAIN_FRAG)) { // Back pressed from BillAmountFrag
            FRAG_LAST = END;       //set last to end
            replaceFragment(new MainFragment());
        }
        else if(FRAG_LAST.equals(FRAG_BILL_AMOUNT)) {   // Back pressed from HowWasSvcFrag
            FRAG_LAST = FRAG_MAIN_FRAG;     //set last to mainFragment
            replaceFragment(new BillAmountFragment());
        }
        else if(FRAG_LAST.equals(FRAG_HOW_WAS_SVC)){     // Back pressed from TipTotalsFrag
            FRAG_LAST = FRAG_BILL_AMOUNT;   //set last to BillAmountFrag
            replaceFragment(new HowWasSvcFragment());
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
