package com.senior.healthcare;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import java.util.Locale;

public class hospital extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final String serviceKey = ApplicationSetting.getServiceKey();
    private static final String hospitalName = ApplicationSetting.getHospitalName();
    private static final String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?hmcNm=" + hospitalName + "&ServiceKey=" + serviceKey;

    private static double cxVl = 0;
    private static double cyVl = 0;
    private static String hmcTelNo;
    private static boolean bcExmdChrgTypeCd;
    private static boolean ccExmdChrgTypeCd;
    private static boolean cvxcaExmdChrgTypeCd;
    private static boolean grenChrgTypeCd;
    private static boolean lvcaExmdChrgTypeCd;
    private static boolean mchkChrgTypeCd;
    private static boolean stmcaExmdChrgTypeCd;
    private static String locAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital);

        //상단 액션바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //지도
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Thread 실행
        new Thread(new Runnable() {
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
                            updateUI();
                        }
                    });
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    // 오류 처리
                }
            }
        }).start();
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
    }

    private void updateUI() {
        // LinearLayout에 필요한 UI 업데이트 작업 수행
        // 예를 들어, TextView 등을 생성하여 추가할 수 있습니다.
        TextView grenChrgType = findViewById(R.id.grenChrgTypeCd);
        if(grenChrgTypeCd) grenChrgType.setText("일반 검진 : 가능");
        else grenChrgType.setText("일반 검진 : 불가능");

        TextView mchkChrgType = findViewById(R.id.mchkChrgTypeCd);
        if(mchkChrgTypeCd) mchkChrgType.setText("구강 검진 : 가능");
        else mchkChrgType.setText("구강 검진 : 불가능");

        TextView bcExmdChrgType = findViewById(R.id.bcExmdChrgTypeCd);
        if(bcExmdChrgTypeCd) bcExmdChrgType.setText("유방암 검진 : 가능");
        else bcExmdChrgType.setText("유방암 검진 : 불가능");

        TextView ccExmdChrgType = findViewById(R.id.ccExmdChrgTypeCd);
        if(ccExmdChrgTypeCd) ccExmdChrgType.setText("대장암 검진 : 가능");
        else ccExmdChrgType.setText("대장암 검진 : 불가능");

        TextView cvxcaExmdChrgType = findViewById(R.id.cvxcaExmdChrgTypeCd);
        if(cvxcaExmdChrgTypeCd) cvxcaExmdChrgType.setText("자궁경부암 검진 : 가능");
        else cvxcaExmdChrgType.setText("자궁경부암 검진 : 불가능");

        TextView stmcaExmdChrgType = findViewById(R.id.stmcaExmdChrgTypeCd);
        if(stmcaExmdChrgTypeCd) stmcaExmdChrgType.setText("위암 검진 : 가능");
        else stmcaExmdChrgType.setText("위암 검진 : 불가능");

        TextView lvcaExmdChrgType = findViewById(R.id.lvcaExmdChrgTypeCd);
        if(lvcaExmdChrgTypeCd) lvcaExmdChrgType.setText("간암 검진 : 가능");
        else lvcaExmdChrgType.setText("간암 검진 : 불가능");

        TextView loc = findViewById(R.id.locAddr);
        loc.setText("주소 : "+locAddr);

        TextView tel = findViewById(R.id.hmcTelNo);
        tel.setText("전화번호 : "+hmcTelNo);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        Log.v("X좌표",String.valueOf(cxVl));
        Log.v("Y좌표",String.valueOf(cyVl));

        LatLng sydney;
        if (cxVl == 0 && cyVl == 0){
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
        Geocoder geocoder = new Geocoder(this,Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocationName(addressString, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            Log.e("GeocodingActivity", "Error during geocoding", e);
        }

        return null;
    }
}
