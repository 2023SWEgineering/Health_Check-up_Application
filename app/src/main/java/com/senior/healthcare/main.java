package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.senior.healthcare.setting.ApplicationSetting;

public class main extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ImageButton checkup_list = findViewById(R.id.checkup_list);
        ImageButton search_hospital = findViewById(R.id.search_hospital);
        ImageButton change_information = findViewById(R.id.change_information);

        ImageButton list_icon = findViewById(R.id.list_icon);
        ImageButton hospital_icon = findViewById(R.id.hospital_icon);
        ImageButton user_icon = findViewById(R.id.user_icon);

        // 건강검진 리스트 클릭 시 건강검진 리스트 창으로 이동
        checkup_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main.class); // main.class 건강검진 리스트 창으로 연동해야함
                startActivity(intent);
            }
        });

        // 리스트 아이콘 클릭 시 건강검진 리스트 창으로 이동
        list_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main.class); // main.class 건강검진 리스트 창으로 연동해야함
                startActivity(intent);
            }
        });

        // 주변 병원 탐색하기 클릭 시 병원 리스트 창으로 이동
        search_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), search.class);
                startActivity(intent);
            }
        });

        // 병원 아이콘 클릭 시 병원 리스트 창으로 이동
        hospital_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), search.class);
                startActivity(intent);
            }
        });

        // 내 정보 변경 클릭 시 년도 선택 창으로 이동
        change_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), birthdate.class);
                startActivity(intent);
            }
        });

        // 유저 아이콘 클릭 시 년도 선택 창으로 이동
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), birthdate.class);
                startActivity(intent);
            }
        });

    }

}
