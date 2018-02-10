package com.list.todo.todolist.Utils;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.list.todo.todolist.R;

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

    public static void setActiveSpinner(final AppCompatActivity app) {
        final Spinner category_spinner = app.findViewById(R.id.category_active);
        final ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(app,
                R.array.active_array,
                android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(mAdapter);
    }

    public static void setCategorySpinner(final AppCompatActivity app, final boolean enabled) {
        final Spinner category_spinner = app.findViewById(R.id.category_name);
        final ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(app,
                R.array.categories_array,
                android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(mAdapter);
        category_spinner.setEnabled(enabled);
    }

}
