package com.dgg.tipme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends SingleFragmentActivity {

    public final static String FRAG_MAIN_FRAG = "frag_main";
    public final static String FRAG_BILL_AMOUNT = "frag_bill_amount";
    public final static String FRAG_HOW_WAS_SVC = "frag_tip_amount";
    public final static String FRAG_TIP_TOTALS = "frag_tip_totals";
    public final static String FRAG_CALCULATOR = "frag_tip_totals";

    public static String Users_Bill = "";
    public static String Users_Service = "";


    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }


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

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // fragment animation
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, someFragment).addToBackStack(currentFrag);
        transaction.commit();
    }

}
