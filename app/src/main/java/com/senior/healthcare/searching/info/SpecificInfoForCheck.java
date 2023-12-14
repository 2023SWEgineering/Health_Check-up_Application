package com.senior.healthcare.searching.info;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.senior.healthcare.R;
import com.senior.healthcare.searching.SearchByUserInfo;
import com.senior.healthcare.setting.ApplicationSetting;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

//IntroduceCheckType에서 다음 을 누르면 나오는 뷰를 관여하는 클래스로 선택한 검진이 가능한 병원의 목록을 보여줌
public class SpecificInfoForCheck extends SpecificInfo{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isParsingDone = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_health);
        LinearLayout loadingLayout = findViewById(R.id.loadingLayout);

        // 로딩 이미지 회전 애니메이션 적용
        applyRotationAnimation();

        //상단 액션바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ImageView back_icon = findViewById(R.id.back_icon);


        //지도
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Thread 실행
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // API에 GET 요청 보내고 XML 결과 받기
                    String xmlData = getXmlFromUrl(API_URL);
                    // XML 파싱하여 변수에 저장
                    parseXml(xmlData);

                    // UI 업데이트는 메인 쓰레드에서 수행해야 합니다.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingLayout.setVisibility(View.GONE);
                            updateUI();
                        }
                    });
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    // 오류 처리
                }
            }
        });
        thread.start();
        try {
            thread.join(); // 쓰레드 작업이 완료될 때까지 기다림
            // 여기에 쓰레드 작업이 완료된 후 수행할 작업 추가
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 오류 처리
        }
    }
}