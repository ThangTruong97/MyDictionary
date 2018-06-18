package com.thangtruong19.mydictionary;

import android.view.View;
import android.widget.Toast;

/**
 * Created by User on 15/05/2018.
 */

public class NumberClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Open the list of listener", Toast.LENGTH_SHORT).show();
    }

}


