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

public class SiGunGuTest extends TestCase {
    private static final String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/CodeService/getSiGunGuList?siDoCd="+ 11 +"&ServiceKey="+ApplicationSetting.getServiceKey()+"&numOfRows=50";

    @Test
    public void testOnMethod() throws IOException, XmlPullParserException {
        String xmlData = getXmlFromUrl(API_URL);
        List<SiGunGuTest.SigunguInfo> siGunGuList = parseXml(xmlData);

        String expected = "종로구";
        assertEquals(expected,siGunGuList.get(0).sigunguNm);
    }

    public List<SiGunGuTest.SigunguInfo> testOnComponent() throws IOException, XmlPullParserException {
        String xmlData = getXmlFromUrl(API_URL);
        List<SiGunGuTest.SigunguInfo> siGunGuList = parseXml(xmlData);
        return siGunGuList;
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
    public class SigunguInfo {
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
}


