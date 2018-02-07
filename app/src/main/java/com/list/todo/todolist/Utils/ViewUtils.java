package com.list.todo.todolist.Utils;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by carlendev on 07/02/18.
 */

public class ViewUtils {


    public static void populateSnackBar(final String msg, final AppCompatActivity app) {
        Snackbar.make(app.findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_LONG)
                .show();
    }

    public static String getTextViewObj(final int entityId, final AppCompatActivity app) {
        TextView textView = app.findViewById(entityId);
        return textView.getText().toString();
    }

}
