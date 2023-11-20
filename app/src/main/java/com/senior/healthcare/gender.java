package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.senior.healthcare.setting.ApplicationSetting;

public class gender extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gender);

        TextView textView = findViewById(R.id.textView);
        textView.setText(String.valueOf(ApplicationSetting.getAge()));

        Button male = findViewById(R.id.male); // yourButtonId는 버튼의 ID

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationSetting.setGender("남자");
                Intent intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
            }
        });

        Button female = findViewById(R.id.female); // yourButtonId는 버튼의 ID

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationSetting.setGender("여자");
                Intent intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
            }
        });
    }
}
