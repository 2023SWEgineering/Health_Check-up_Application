package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.senior.healthcare.setting.ApplicationSetting;

public class gender extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gender);

        Button male = findViewById(R.id.male);
        Button female = findViewById(R.id.female);
        Button button = findViewById(R.id.button);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationSetting.setGender("남자");
                male.setSelected(true);
                female.setSelected(false);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationSetting.setGender("여자");
                male.setSelected(false);
                female.setSelected(true);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), sido.class);
                startActivity(intent);
            }
        });
    }
}
