package com.senior.healthcare.searching;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.senior.healthcare.R;
import com.senior.healthcare.searching.HealthCheckList;
import com.senior.healthcare.searching.SearchByUserInfo;

public class IntroduceCheckType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String checkType = bundle.getString("checkType");
        setContentView(R.layout.check_spec_type);

        ImageView back_img = findViewById(R.id.back_img);

        if (checkType.equals("자궁경부암")) {
            back_img.setImageResource(R.drawable.cervical_cancer);
        } else if (checkType.equals("유방암")) {
            back_img.setImageResource(R.drawable.breast_cancer);
        } else if (checkType.equals("대장암")) {
            back_img.setImageResource(R.drawable.colon_cancer);
        } else if (checkType.equals("간암")) {
            back_img.setImageResource(R.drawable.liver_cancer);
        } else if (checkType.equals("위암")) {
            back_img.setImageResource(R.drawable.stomach_cancer);
        } else if (checkType.equals("구강")) {
            back_img.setImageResource(R.drawable.oral_examination);
        } else if (checkType.equals("일반")) {
            back_img.setImageResource(R.drawable.common_check);
        }

        TextView textView = findViewById(R.id.textSpecByType);
        textView.setText(checkType + " 검진");

        //상단 액션바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ImageView back_icon = findViewById(R.id.back_icon);
        Button button = findViewById(R.id.nextButton);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle bundle = new Bundle();
                bundle.putString("checkType", checkType);
                Intent intent = new Intent(getApplicationContext(), SearchByUserInfo.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}