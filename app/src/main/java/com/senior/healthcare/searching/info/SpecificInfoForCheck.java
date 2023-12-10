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
import java.util.List;

//IntroduceCheckType에서 다음 을 누르면 나오는 뷰를 관여하는 클래스로 선택한 검진이 가능한 병원의 목록을 보여줌
public class SpecificInfoForCheck extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String serviceKey = ApplicationSetting.getServiceKey();
    private String hospitalName = ApplicationSetting.getHospitalName();
    private String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?hmcNm=" + hospitalName + "&ServiceKey=" + serviceKey;

    private double cxVl = 0;
    private double cyVl = 0;
    private String hmcTelNo;
    private boolean bcExmdChrgTypeCd;
    private boolean ccExmdChrgTypeCd;
    private boolean cvxcaExmdChrgTypeCd;
    private boolean grenChrgTypeCd;
    private boolean lvcaExmdChrgTypeCd;
    private boolean mchkChrgTypeCd;
    private boolean stmcaExmdChrgTypeCd;
    private String locAddr;
    private boolean isParsingDone;
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

    private void applyRotationAnimation() {
        ImageView loadingImageView = findViewById(R.id.loadingImageView);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);  // 회전 애니메이션의 지속 시간 (밀리초)
        rotateAnimation.setRepeatCount(Animation.INFINITE);  // 무한 반복
        loadingImageView.startAnimation(rotateAnimation);
    }

    private String getXmlFromUrl(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result.toString();
    }

    private void parseXml(String xmlData) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new java.io.StringReader(xmlData));

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && "item".equals(parser.getName())) {
                while (eventType != XmlPullParser.END_TAG || !"item".equals(parser.getName())) {
                    if (eventType == XmlPullParser.START_TAG) {
                        switch (parser.getName()) {
                            case "cxVl":
                                cxVl = Double.parseDouble(parser.nextText());
                                break;
                            case "cyVl":
                                cyVl = Double.parseDouble(parser.nextText());
                                break;
                            case "hmcTelNo":
                                hmcTelNo = parser.nextText();
                                break;
                            case "bcExmdChrgTypeCd":
                                bcExmdChrgTypeCd = "1".equals(parser.nextText());
                                break;
                            case "ccExmdChrgTypeCd":
                                ccExmdChrgTypeCd = "1".equals(parser.nextText());
                                break;
                            case "cvxcaExmdChrgTypeCd":
                                cvxcaExmdChrgTypeCd = "1".equals(parser.nextText());
                                break;
                            case "grenChrgTypeCd":
                                grenChrgTypeCd = "1".equals(parser.nextText());
                                break;
                            case "lvcaExmdChrgTypeCd":
                                lvcaExmdChrgTypeCd = "1".equals(parser.nextText());
                                break;
                            case "mchkChrgTypeCd":
                                mchkChrgTypeCd = "1".equals(parser.nextText());
                                break;
                            case "stmcaExmdChrgTypeCd":
                                stmcaExmdChrgTypeCd = "1".equals(parser.nextText());
                                break;
                            case "locAddr":
                                locAddr = parser.nextText();
                                break;
                        }
                    }
                    eventType = parser.next();
                }
            }
            eventType = parser.next();
        }
        isParsingDone = true;
    }

    private void updateUI() {
        // LinearLayout에 필요한 UI 업데이트 작업 수행
        // 예를 들어, TextView 등을 생성하여 추가할 수 있습니다.
        TextView grenChrgType = findViewById(R.id.grenChrgTypeCd);
        if (grenChrgTypeCd) grenChrgType.setText("일반 검진 : 가능");
        else grenChrgType.setText("일반 검진 : 불가능");

        TextView mchkChrgType = findViewById(R.id.mchkChrgTypeCd);
        if (mchkChrgTypeCd) mchkChrgType.setText("구강 검진 : 가능");
        else mchkChrgType.setText("구강 검진 : 불가능");

        TextView bcExmdChrgType = findViewById(R.id.bcExmdChrgTypeCd);
        if (bcExmdChrgTypeCd) bcExmdChrgType.setText("유방암 검진 : 가능");
        else bcExmdChrgType.setText("유방암 검진 : 불가능");

        TextView ccExmdChrgType = findViewById(R.id.ccExmdChrgTypeCd);
        if (ccExmdChrgTypeCd) ccExmdChrgType.setText("대장암 검진 : 가능");
        else ccExmdChrgType.setText("대장암 검진 : 불가능");

        TextView cvxcaExmdChrgType = findViewById(R.id.cvxcaExmdChrgTypeCd);
        if (cvxcaExmdChrgTypeCd) cvxcaExmdChrgType.setText("자궁경부암 검진 : 가능");
        else cvxcaExmdChrgType.setText("자궁경부암 검진 : 불가능");

        TextView stmcaExmdChrgType = findViewById(R.id.stmcaExmdChrgTypeCd);
        if (stmcaExmdChrgTypeCd) stmcaExmdChrgType.setText("위암 검진 : 가능");
        else stmcaExmdChrgType.setText("위암 검진 : 불가능");

        TextView lvcaExmdChrgType = findViewById(R.id.lvcaExmdChrgTypeCd);
        if (lvcaExmdChrgTypeCd) lvcaExmdChrgType.setText("간암 검진 : 가능");
        else lvcaExmdChrgType.setText("간암 검진 : 불가능");

        TextView loc = findViewById(R.id.locAddr);
        loc.setText(locAddr);

        TextView tel = findViewById(R.id.hmcTelNo);
        tel.setText(hmcTelNo);

        grenChrgType.setTypeface(null, Typeface.BOLD);
        grenChrgType.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
        mchkChrgType.setTypeface(null, Typeface.BOLD);
        mchkChrgType.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
        bcExmdChrgType.setTypeface(null, Typeface.BOLD);
        bcExmdChrgType.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
        ccExmdChrgType.setTypeface(null, Typeface.BOLD);
        ccExmdChrgType.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
        cvxcaExmdChrgType.setTypeface(null, Typeface.BOLD);
        cvxcaExmdChrgType.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
        stmcaExmdChrgType.setTypeface(null, Typeface.BOLD);
        stmcaExmdChrgType.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
        lvcaExmdChrgType.setTypeface(null, Typeface.BOLD);
        lvcaExmdChrgType.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
        loc.setTypeface(null, Typeface.BOLD);
        loc.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
        tel.setTypeface(null, Typeface.BOLD);
        tel.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));

        TextView hospital_name = findViewById(R.id.hospital_name);
        String hospitalNameText = ApplicationSetting.getHospitalName();

        int totalLength = hospitalNameText.length();
        int maxCharsPerLine = 12;
        int maxLines = 2; // 원하는 최대 줄 수

        StringBuilder formattedText = new StringBuilder();
        int startIndex = 0;
        int endIndex;


        while (startIndex < totalLength) {
            endIndex = Math.min(startIndex + maxCharsPerLine, totalLength);
            formattedText.append(hospitalNameText, startIndex, endIndex).append("\n");

            startIndex = endIndex;

            // 추가: 특정 길이 이상이면 최대 줄 수를 증가시킴
            if (endIndex >= 24) {
                maxLines++;
                maxCharsPerLine = totalLength / maxLines;
            }
        }

        hospital_name.setText(formattedText.toString().trim());
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"전화번호가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hmcTelNo));
                startActivity(dialIntent);
            }
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        while (isParsingDone == false) {
            Log.v("X좌표", String.valueOf(cxVl));
            Log.v("Y좌표", String.valueOf(cyVl));
            if (cxVl != 0 && cyVl != 0)break;
        }
        LatLng sydney;
        if (cxVl == 0 && cyVl == 0) {
            sydney = performGeocoding(locAddr);
        } else {
            sydney = new LatLng(cxVl, cyVl);
        }
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title(ApplicationSetting.getHospitalName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }

    private LatLng performGeocoding(String addressString) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> r = geocoder.getFromLocationName(addressString, 1);
            double lat = r.get(0).getLatitude();
            double lng = r.get(0).getLongitude();
            Log.d("@@", "위도 : " + lat + ", 경도 : " + lng);
            LatLng latLng = new LatLng(lat,lng);

            return latLng;

        } catch (Exception e) {
            Log.d("@@", "주소변환 실패");
        }
        return null;
    }
}