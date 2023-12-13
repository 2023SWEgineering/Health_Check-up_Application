package com.senior.healthcare.unit;
import com.senior.healthcare.setting.ApplicationSetting;

import junit.framework.TestCase;

import org.junit.Test;
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

public class SiDoTest extends TestCase {
    private static final String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/CodeService/getSiDoList?ServiceKey="+ ApplicationSetting.getServiceKey()+"&numOfRows=20";

    @Test
    public void testOnMethod() throws IOException, XmlPullParserException {
        String xmlData = getXmlFromUrl(API_URL);
        List<SidoInfo> sidoList = parseXml(xmlData);

        String expected = "서울특별시";
        assertEquals(expected, sidoList.get(0).sidoNm);
    }

    public List<SidoInfo> testOnComponent() throws IOException, XmlPullParserException {
        String xmlData = getXmlFromUrl(API_URL);
        List<SidoInfo> sidoList = parseXml(xmlData);
        return sidoList;
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


    public class SidoInfo {
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

}