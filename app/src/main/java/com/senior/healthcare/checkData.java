package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.senior.healthcare.setting.ApplicationSetting;

public class checkData extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 레이아웃을 생성하거나, 기존의 레이아웃을 사용합니다.
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        layout.setOrientation(LinearLayout.VERTICAL);

        // Age
        TextView age = new TextView(this);
        age.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        age.setText(String.valueOf(ApplicationSetting.getAge()));
        layout.addView(age);

        // Gender
        TextView gender = new TextView(this);
        gender.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        gender.setText(String.valueOf(ApplicationSetting.getGender()));
        layout.addView(gender);

        // City
        TextView city = new TextView(this);
        city.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        city.setText(ApplicationSetting.getCity());
        layout.addView(city);

        // Viliage
        TextView village = new TextView(this);
        village.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        village.setText(ApplicationSetting.getVillage());
        layout.addView(village);

        // 안내
        TextView check = new TextView(this);
        check.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        check.setText("입력한 정보가 올바른지 확인해주세요.");
        layout.addView(check);

        Button yes = new Button(this);
        yes.setText("예");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
            }
        });
        layout.addView(yes);

        Button no = new Button(this);
        no.setText("아니요");
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), birthdate.class);
                startActivity(intent);
            }
        });
        layout.addView(no);

        setContentView(layout);
    }
}
