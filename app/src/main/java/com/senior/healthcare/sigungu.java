package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class sigungu extends Activity {

    private static final String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/CodeService/getSiGunGuList?siDoCd="+ApplicationSetting.getCityCode()+"&ServiceKey=eOIFq%2FdyWqgEH46rcHoOaXBRiKvn9XwDy8I2ISFT5dBWNSPgI%2BCty58H1nxLT81K0UL2zPVwIh40FNfXlBTsRg%3D%3D&numOfRows=50";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sigungu);

        // Thread 실행
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // API에 GET 요청 보내고 XML 결과 받기
                    String xmlData = getXmlFromUrl(API_URL);

                    // XML 파싱하여 sidoNm, sidoCd 값 추출
                    final List<SigunguInfo> sigunguList = parseXml(xmlData);

                    // UI 업데이트는 메인 쓰레드에서 수행해야 합니다.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 파싱된 결과를 사용하여 버튼 동적 생성
                            createButtons(sigunguList);
                        }
                    });
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    // 오류 처리
                    Log.e("SigunguActivity", "오류 발생");
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

    private List<SigunguInfo> parseXml(String xmlData) throws XmlPullParserException, IOException {
        List<SigunguInfo> sigunguList = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new java.io.StringReader(xmlData));

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && "item".equals(parser.getName())) {
                SigunguInfo sigunguInfo = new SigunguInfo();
                while (eventType != XmlPullParser.END_TAG || !"item".equals(parser.getName())) {
                    if (eventType == XmlPullParser.START_TAG && "siGunGuNm".equals(parser.getName())) {
                        sigunguInfo.setSigunguNm(parser.nextText());
                    } else if (eventType == XmlPullParser.START_TAG && "siGunGuCd".equals(parser.getName())) {
                        sigunguInfo.setSigunguCd(parser.nextText());
                    }
                    eventType = parser.next();
                }
                sigunguList.add(sigunguInfo);
            }
            eventType = parser.next();
        }

        return sigunguList;
    }

    private void createButtons(List<SigunguInfo> sigunguList) {
        LinearLayout layout = findViewById(R.id.sigungu);

        for (final SigunguInfo sigunguInfo : sigunguList) {
            Button button = new Button(this);
            button.setText(sigunguInfo.getSigunguNm());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 버튼을 누를 때 ApplicationSetting에 값을 저장
                    ApplicationSetting.setVillage(sigunguInfo.getSigunguNm());
                    ApplicationSetting.setVillageCode(sigunguInfo.getSigunguCd());
                    Intent intent = new Intent(getApplicationContext(), checkData.class);
                    startActivity(intent);
                    // 여기에서 필요한 추가 작업 수행
                }
            });

            layout.addView(button);
        }
    }
}

class SigunguInfo {
    private String sigunguNm;
    private String sigunguCd;

    public String getSigunguNm() {
        return sigunguNm;
    }

    public void setSigunguNm(String sigunguNm) {
        this.sigunguNm = sigunguNm;
    }

    public String getSigunguCd() {
        return sigunguCd;
    }

    public void setSigunguCd(String sigunguCd) {
        this.sigunguCd = sigunguCd;
    }
}
