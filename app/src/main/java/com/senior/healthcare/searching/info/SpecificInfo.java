package com.senior.healthcare.searching.info;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.senior.healthcare.R;
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

public abstract class SpecificInfo extends AppCompatActivity implements OnMapReadyCallback {
    protected GoogleMap mMap;

    protected String serviceKey = ApplicationSetting.getServiceKey();
    protected String hospitalName = ApplicationSetting.getHospitalName();
    protected String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?hmcNm=" + URLEncoder.encode(hospitalName) + "&siDoCd="+ApplicationSetting.getCityCode() + "&siGunGuCd=" + ApplicationSetting.getVillageCode() +"&ServiceKey=" + serviceKey;

    protected double cxVl = 0;
    protected double cyVl = 0;
    protected String hmcTelNo;
    protected boolean bcExmdChrgTypeCd;
    protected boolean ccExmdChrgTypeCd;
    protected boolean cvxcaExmdChrgTypeCd;
    protected boolean grenChrgTypeCd;
    protected boolean lvcaExmdChrgTypeCd;
    protected boolean mchkChrgTypeCd;
    protected boolean stmcaExmdChrgTypeCd;
    protected String locAddr;
    protected boolean isParsingDone;
    
    protected void updateUI() {
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

    protected void applyRotationAnimation() {
        ImageView loadingImageView = findViewById(R.id.loadingImageView);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);  // 회전 애니메이션의 지속 시간 (밀리초)
        rotateAnimation.setRepeatCount(Animation.INFINITE);  // 무한 반복
        loadingImageView.startAnimation(rotateAnimation);
    }

    protected String getXmlFromUrl(String urlString) throws IOException {
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
    protected void parseXml(String xmlData) throws XmlPullParserException, IOException, NumberFormatException {
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
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    protected LatLng performGeocoding(String addressString) {
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
