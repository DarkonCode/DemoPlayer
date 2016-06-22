package com.demo.demoplayer.utils;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

/**
 * Created by Brandon Lee Roets  on 2016/06/20.
 */
public class Utilities {

    public static void showSnackBar(String textToDisplay, CoordinatorLayout coordinatorLayout) {
        Snackbar.make(coordinatorLayout, textToDisplay, Snackbar.LENGTH_LONG).show();
    }
}
