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

public class SiGunGu extends Activity {

    private static final String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/CodeService/getSiGunGuList?siDoCd="+ApplicationSetting.getCityCode()+"&ServiceKey="+ApplicationSetting.getServiceKey()+"&numOfRows=50";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sigungu);
        LinearLayout loadingLayout = findViewById(R.id.loadingLayout);

        // 로딩 이미지 회전 애니메이션 적용
        applyRotationAnimation();

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
                            loadingLayout.setVisibility(View.GONE);
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

        Button sigungu_button = findViewById(R.id.sigungu_button);

        sigungu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckData.class);
                startActivity(intent);
            }
        });
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
        TableLayout layout = findViewById(R.id.sigungu);

        // TableRow를 동적으로 생성하여 TableLayout에 추가
        TableRow tableRow = new TableRow(this);
        layout.addView(tableRow);

        int buttonCount = 0;

        for (final SigunguInfo sigunguInfo : sigunguList) {
            Button button = new Button(this);

            button.setBackgroundResource(R.drawable.sido_sigungu_button);

            String btnText = sigunguInfo.getSigunguNm();

            if (btnText.equals("창원시 의창구")) {
                btnText = "의창구";
            } else if (btnText.equals("창원시 성산구")) {
                btnText = "성산구";
            } else if (btnText.equals("창원시 마산합포구")) {
                btnText = "마산 합포구";
            } else if (btnText.equals("창원시 마산회원구")) {
                btnText = "마산 회원구";
            } else if (btnText.equals("창원시 진해구")) {
                btnText = "진해구";
            } else if (btnText.equals("청주시 상당구")) {
                btnText = "청주 상당구";
            } else if (btnText.equals("청주시 서원구")) {
                btnText = "청주 서원구";
            } else if (btnText.equals("청주시 흥덕구")) {
                btnText = "청주 흥덕구";
            } else if (btnText.equals("청주시 청원구")) {
                btnText = "청주 청원구";
            }

            button.setText(btnText);

            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));

            button.setTypeface(null, Typeface.BOLD);

            TableRow.LayoutParams params = new TableRow.LayoutParams(306, 170);

            params.setMargins(10, 10, 10, 10);

            button.setLayoutParams(params);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i=0; i<layout.getChildCount(); i++) {
                        TableRow row = (TableRow) layout.getChildAt(i);
                        for (int j=0; j<row.getChildCount(); j++) {
                            View child = row.getChildAt(j);
                            if (child instanceof Button) {
                                child.setSelected(false);
                            }
                        }
                    }
                    button.setSelected(true);
                    ApplicationSetting.setVillage(sigunguInfo.getSigunguNm());
                    ApplicationSetting.setVillageCode(sigunguInfo.getSigunguCd());
                    // 버튼을 누를 때 ApplicationSetting에 값을 저장
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
