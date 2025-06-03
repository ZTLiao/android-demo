package com.example.demo;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class SubActivity02 extends ComponentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub02);
        Log.i(SubActivity02.class.getSimpleName(), "subActivity02 onCreate");
    }
}
