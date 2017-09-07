package com.termal.ayojaki.utils;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.termal.ayojaki.App;
import com.termal.ayojaki.R;


/**
 * Created by akbar on 26/07/17.
 */

public class ViewUtils {

    public static void showSnackbar(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showError(String message){
        new AlertDialog.Builder(App.getContext())
                .setTitle(App.getContext().getString(R.string.app_name))
                .setMessage(message)
                .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}
