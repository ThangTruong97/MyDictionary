package com.thangtruong19.mydictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class NumbersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, new NumbersFragment())
        .commit();
        }
}


