package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //라우팅
        Intent intent = new Intent(getApplicationContext(), enterScreen.class);
        startActivity(intent);
    }
}

