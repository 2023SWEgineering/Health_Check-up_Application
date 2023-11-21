package com.senior.healthcare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class hospital extends Activity {

    private static final String serviceKey = ApplicationSetting.getServiceKey();
    private static final String hospitalName = ApplicationSetting.getHospitalName();
    private static final String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?hmcNm=" + hospitalName + "&ServiceKey=" + serviceKey;

    private static double cxVl;
    private static double cyVl;
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

        // UI 업데이트를 위한 LinearLayout 생성
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

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
                            // LinearLayout에 TextView 등을 추가하여 UI 업데이트
                            updateUI(linearLayout);

                            // 생성한 LinearLayout을 화면에 표시
                            setContentView(linearLayout);
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

    private void updateUI(LinearLayout linearLayout) {
        // LinearLayout에 필요한 UI 업데이트 작업 수행
        // 예를 들어, TextView 등을 생성하여 추가할 수 있습니다.
        TextView textView = new TextView(this);
        textView.setText("유방암: " + bcExmdChrgTypeCd +
                "\n대장암: " + ccExmdChrgTypeCd +
                "\n자궁경부암: " + cvxcaExmdChrgTypeCd +
                "\n일반검진: " + grenChrgTypeCd +
                "\n간암: " + lvcaExmdChrgTypeCd +
                "\n구강검진: " + mchkChrgTypeCd +
                "\n위암검진: " + stmcaExmdChrgTypeCd +
                "\n주소: " + locAddr +
                "\n전화번호: " + hmcTelNo +
                "\nX좌표: " + cxVl +
                "\nY좌표: " + cyVl);
        linearLayout.addView(textView);
    }
}
