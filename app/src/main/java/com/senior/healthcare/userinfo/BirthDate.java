package com.senior.healthcare.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.senior.healthcare.R;
import com.senior.healthcare.setting.ApplicationSetting;


public class BirthDate extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthdate);

        //2023 하드코딩 수정 필요
        int thisYear = 2023;

        //year
        Integer[] year = new Integer[104];
        for(int i = 0 ; i < 104 ; i++) year[i] = 2003 - i;

        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_item_layout, year) {
            @Override
            public View getView(int position, View converView, ViewGroup parent) {
                View view = super.getView(position, converView, parent);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // 드롭다운 리스트에서도 동일한 레이아웃을 사용
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };
        //yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        Spinner yearSpinner = findViewById(R.id.spinner_year);
        yearSpinner.setAdapter(yearAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                int value = (int) parentView.getItemAtPosition(position);
                ApplicationSetting.setAge(thisYear-value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button join = (Button) findViewById(R.id.button);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Gender.class);
                startActivity(intent);
            }
        });
    }
}
