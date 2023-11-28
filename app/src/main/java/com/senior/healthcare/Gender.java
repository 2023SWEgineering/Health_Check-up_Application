package com.senior.healthcare;

import static android.widget.Toast.makeText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.senior.healthcare.setting.ApplicationSetting;

public class Gender extends Activity {

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
                if (ApplicationSetting.getGender() == null) {
                    Toast.makeText(getApplicationContext(),"성별을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), SiDo.class);
                    startActivity(intent);
                }
            }
        });
    }
}
