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