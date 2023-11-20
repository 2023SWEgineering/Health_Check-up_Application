package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.senior.healthcare.setting.ApplicationSetting;


public class birthdate extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button join;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthdate);

        //2023 하드코딩 수정 필요
        int thisYear = 2023;

        //year
        Integer[] year = new Integer[104];
        for(int i = 0 ; i < 104 ; i++) year[i] = i+1900;

        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, year);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        join = (Button) findViewById(R.id.button);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),gender.class);
                startActivity(intent);
            }
        });
    }
}
