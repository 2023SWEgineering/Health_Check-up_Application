package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.senior.healthcare.setting.ApplicationSetting;

public class CheckData extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.checkdata);

        TextView age__gender_check = findViewById(R.id.age_gender_check);
        TextView region_check = findViewById(R.id.region_check);

        age__gender_check.setText(String.valueOf(ApplicationSetting.getAge()) + "세" + " " + String.valueOf(ApplicationSetting.getGender()));
        region_check.setText(String.valueOf(ApplicationSetting.getCity()) + " " + String.valueOf(ApplicationSetting.getVillage()));

        Button yes = findViewById(R.id.yes);
        Button no = findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BirthDate.class);
                startActivity(intent);
            }
        });
    }
}
