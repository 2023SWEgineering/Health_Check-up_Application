package com.senior.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

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

public class sido extends Activity {

    private static final String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/CodeService/getSiDoList?ServiceKey="+ApplicationSetting.getServiceKey()+"&numOfRows=20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sido);

        // Thread 실행
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // API에 GET 요청 보내고 XML 결과 받기
                    String xmlData = getXmlFromUrl(API_URL);

                    // XML 파싱하여 sidoNm, sidoCd 값 추출
                    final List<SidoInfo> sidoList = parseXml(xmlData);

                    // UI 업데이트는 메인 쓰레드에서 수행해야 합니다.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 파싱된 결과를 사용하여 버튼 동적 생성
                            createButtons(sidoList);
                        }
                    });
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    // 오류 처리
                    Log.e("SidoActivity", "오류 발생");
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

    private List<SidoInfo> parseXml(String xmlData) throws XmlPullParserException, IOException {
        List<SidoInfo> sidoList = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new java.io.StringReader(xmlData));

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && "item".equals(parser.getName())) {
                SidoInfo sidoInfo = new SidoInfo();
                while (eventType != XmlPullParser.END_TAG || !"item".equals(parser.getName())) {
                    if (eventType == XmlPullParser.START_TAG && "siDoNm".equals(parser.getName())) {
                        sidoInfo.setSidoNm(parser.nextText());
                    } else if (eventType == XmlPullParser.START_TAG && "siDoCd".equals(parser.getName())) {
                        sidoInfo.setSidoCd(parser.nextText());
                    }
                    eventType = parser.next();
                }
                sidoList.add(sidoInfo);
            }
            eventType = parser.next();
        }

        return sidoList;
    }

    private void createButtons(List<SidoInfo> sidoList) {
        TableLayout layout = findViewById(R.id.sido);

        // TableRow를 동적으로 생성하여 TableLayout에 추가
        TableRow tableRow = new TableRow(this);
        layout.addView(tableRow);

        int buttonCount = 0;

        for (final SidoInfo sidoInfo : sidoList) {
            // 버튼을 생성하여 TableRow에 추가
            if (sidoInfo.getSidoNm().equals("황해도")) continue;
            Button button = new Button(this);

            String btnText = sidoInfo.getSidoNm();

            button.setText(btnText);

            if (btnText.length() > 5) {
                button.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
            }

            button.setTypeface(null, Typeface.BOLD);

            TableRow.LayoutParams params = new TableRow.LayoutParams(306, 170);

            button.setLayoutParams(params);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 버튼을 누를 때 ApplicationSetting에 값을 저장
                    ApplicationSetting.setCity(sidoInfo.getSidoNm());
                    ApplicationSetting.setCityCode(sidoInfo.getSidoCd());
                    if(sidoInfo.getSidoNm().equals("세종특별자치시")) {
                        Intent intent = new Intent(getApplicationContext(), checkData.class);
                        ApplicationSetting.setVillageCode("110");
                        ApplicationSetting.setVillage("");
                        startActivity(intent);
                    } else{
                        Intent intent = new Intent(getApplicationContext(), sigungu.class);
                        startActivity(intent);
                    }
                    // 여기에서 필요한 추가 작업 수행
                }
            });

            tableRow.addView(button);  // TableRow에 버튼 추가
            buttonCount++;

            // 한 TableRow에 3개의 버튼이 들어가면 새로운 TableRow 생성
            if (buttonCount == 3) {
                tableRow = new TableRow(this);
                layout.addView(tableRow);
                buttonCount = 0;  // 버튼 개수 초기화
            }
        }
    }
}

class SidoInfo {
    private String sidoNm;
    private String sidoCd;

    public String getSidoNm() {
        return sidoNm;
    }

    public void setSidoNm(String sidoNm) {
        this.sidoNm = sidoNm;
    }

    public String getSidoCd() {
        return sidoCd;
    }

    public void setSidoCd(String sidoCd) {
        this.sidoCd = sidoCd;
    }
}
