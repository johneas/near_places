package com.gap.test.view.common.base;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.gap.test.view.main.MainActivity;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */
public class BaseFragment extends Fragment {

    /**
     * Show a message to user
     *
     * @param message message
     */
    public void displayMessage(String message) {
        getActivity().onBackPressed();
        ((MainActivity) getActivity()).displayMessage(message);
    }

    /**
     * Set a title for current view
     *
     * @param title Title, this must be a string resource
     */
    public void setTitle(@StringRes int title){
        getActivity().setTitle(title);
    }

    /**
     * Set a title for current view
     *
     * @param title Title
     */
    public void setTitle(String title){
        getActivity().setTitle(title);
    }

    /**
     * Set or remove the back arrow in Tool bar
     *
     * @param isBackArrow define if the back arrow will be visible
     */
    public void setBacArrow(Boolean isBackArrow){
        ((MainActivity) getActivity()).setBackArrow(isBackArrow);
    }

    /**
     * Hide the key pad
     */
    public void hideKeyPad(){
        ((MainActivity) getActivity()).hideKeyPad();
    }

    public void hideSnackBar(){
        ((MainActivity) getActivity()).hideSnackBar();
    }
}
