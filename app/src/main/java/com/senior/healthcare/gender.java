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

        //TextView textView = findViewById(R.id.textView);
        //textView.setText(String.valueOf(ApplicationSetting.getAge()));

        Button male = findViewById(R.id.male); // yourButtonId는 버튼의 ID
        Button female = findViewById(R.id.female); // yourButtonId는 버튼의 ID
        Button button = findViewById(R.id.button);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationSetting.setGender("남자");
                //Intent intent = new Intent(getApplicationContext(), firstcheckData.class);
                //startActivity(intent);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationSetting.setGender("여자");
                //Intent intent = new Intent(getApplicationContext(), checkData.class);
                //startActivity(intent);
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
