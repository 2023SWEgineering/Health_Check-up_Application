package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import java.util.ArrayList;
import java.util.List;

public class Search extends Activity {
    private static final String serviceKey = ApplicationSetting.getServiceKey();
    private static final String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?siDoCd=" + ApplicationSetting.getCityCode() + "&siGunGuCd=" + ApplicationSetting.getVillageCode() + "&numOfRows=300&ServiceKey=" + serviceKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        LinearLayout loadingLayout = findViewById(R.id.loadingLayout);

        // 로딩 이미지 회전 애니메이션 적용
        applyRotationAnimation();

        ImageView back_icon = findViewById(R.id.back_icon);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
            }
        });

        // Thread 실행
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // API에 GET 요청 보내고 XML 결과 받기
                    String xmlData = getXmlFromUrl(API_URL);

                    // XML 파싱하여 hospitalName, hospitalCode 값 추출
                    final List<HospitalInfo> hospitalList = parseXml(xmlData);

                    // UI 업데이트는 메인 쓰레드에서 수행해야 합니다.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingLayout.setVisibility(View.GONE);
                            // 파싱된 결과를 사용하여 버튼 동적 생성
                            createButtons(hospitalList);
                        }
                    });
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    // 오류 처리
                    Log.e("search", "오류 발생");
                }
            }
        }).start();
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

    private List<HospitalInfo> parseXml(String xmlData) throws XmlPullParserException, IOException {
        List<HospitalInfo> hospitalList = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new java.io.StringReader(xmlData));

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && "item".equals(parser.getName())) {
                HospitalInfo hospitalInfo = new HospitalInfo();
                while (eventType != XmlPullParser.END_TAG || !"item".equals(parser.getName())) {
                    if (eventType == XmlPullParser.START_TAG && "hmcNm".equals(parser.getName())) {
                        hospitalInfo.setHospitalName(parser.nextText());
                    } else if (eventType == XmlPullParser.START_TAG && "hmcNo".equals(parser.getName())) {
                        hospitalInfo.setHospitalCode(parser.nextText());
                    }
                    eventType = parser.next();
                }
                hospitalList.add(hospitalInfo);
            }
            eventType = parser.next();
        }

        return hospitalList;
    }

    private void createButtons(List<HospitalInfo> hospitalList) {
        LinearLayout layout = findViewById(R.id.search);

        // 이미지 리소스 배열
        int[] buttonBackgrounds = {R.drawable.btn_color1, R.drawable.btn_color2, R.drawable.btn_color3,
                R.drawable.btn_color4, R.drawable.btn_color5, R.drawable.btn_color6};

        int imageIndex = 0; // 이미지 인덱스 변수

        for (final HospitalInfo hospitalInfo : hospitalList) {
            Button button = new Button(this);

            String btnText = hospitalInfo.getHospitalName();
            
            //글자 길이에 따라 ... 붙이기
            if (btnText.length() > 10) {
                btnText = btnText.substring(0, 10) + "...";
            }
            button.setText(btnText);

            //굵게, 글자 크기 조절
            button.setTypeface(null, Typeface.BOLD);
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
            // 순서대로 이미지 선택
            button.setBackgroundResource(buttonBackgrounds[imageIndex]);

            // 이미지 인덱스 업데이트
            imageIndex = (imageIndex + 1) % buttonBackgrounds.length;

            //버튼 크기 조절
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(920, 185);

            params.setMargins(0, 0, 0, 40);
            button.setLayoutParams(params);

            button.setOnClickListener(view -> {
                ApplicationSetting.setHospitalName(hospitalInfo.getHospitalName());
                ApplicationSetting.setHospitalCode(hospitalInfo.getHospitalCode());

                Intent intent = new Intent(getApplicationContext(), Hospital.class); // 변경된 클래스명으로 수정
                startActivity(intent);
            });

            layout.addView(button);
        }
    }
}

class HospitalInfo {

    private String hospitalName;
    private String hospitalCode;

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
}
