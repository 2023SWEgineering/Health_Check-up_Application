package com.senior.healthcare.unit;

import com.senior.healthcare.HospitalInfo;
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

public class SearchByLocationTest extends TestCase {

    @Test
    public void testOnCreate() throws IOException, XmlPullParserException {
        String sidoCd = "11";
        String siGunGu = "110";
        String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?siDoCd=" + sidoCd + "&siGunGuCd=" + siGunGu + "&numOfRows=300&ServiceKey=" + ApplicationSetting.getServiceKey();
        String xmlData = getXmlFromUrl(API_URL);
        List<HospitalInfo> hospitalInfos = parseXml(xmlData);

        String expected = "강북삼성병원";
        assertEquals(expected, hospitalInfos.get(0).getHospitalName());
    }

    public List<HospitalInfo> testOnComponent(String sidoCd, String siGunGu) throws IOException, XmlPullParserException {
        String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?siDoCd=" + sidoCd + "&siGunGuCd=" + siGunGu + "&numOfRows=300&ServiceKey=" + ApplicationSetting.getServiceKey();
        String xmlData = getXmlFromUrl(API_URL);
        List<HospitalInfo> hospitalInfos = parseXml(xmlData);
        return hospitalInfos;
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
}