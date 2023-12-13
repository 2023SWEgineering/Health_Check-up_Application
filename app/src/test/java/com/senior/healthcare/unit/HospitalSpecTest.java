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
import java.net.URLEncoder;

public class HospitalSpecTest extends TestCase {

    private double cxVl = 0;
    private double cyVl = 0;
    private String hmcTelNo;
    private boolean bcExmdChrgTypeCd;
    private boolean ccExmdChrgTypeCd;
    private boolean cvxcaExmdChrgTypeCd;
    private boolean grenChrgTypeCd;
    private boolean lvcaExmdChrgTypeCd;
    private boolean mchkChrgTypeCd;
    private boolean stmcaExmdChrgTypeCd;
    private String locAddr;
    private boolean isParsingDone;

    @Test
    public void testOnCreate() throws IOException, XmlPullParserException {
        String hospitalNm = "강북삼성병원";
        String sidoCd = "11";
        String siGunGuCd = "110";

        String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?hmcNm=" + URLEncoder.encode(hospitalNm) + "&siDoCd="+ sidoCd + "&siGunGuCd=" + siGunGuCd +"&ServiceKey=" + ApplicationSetting.getServiceKey();
        String xmlData = getXmlFromUrl(API_URL);
        parseXml(xmlData);
        String expected = "02-2001-2001";
        assertEquals(expected, hmcTelNo);
    }

    public String testOnComponent(String hospitalNm, String sidoCd, String siGunGuCd) throws IOException, XmlPullParserException {
        String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?hmcNm=" + URLEncoder.encode(hospitalNm) + "&siDoCd="+ sidoCd + "&siGunGuCd=" + siGunGuCd +"&ServiceKey=" + ApplicationSetting.getServiceKey();
        String xmlData = getXmlFromUrl(API_URL);
        parseXml(xmlData);
        return hmcTelNo;
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
        isParsingDone = true;
    }

}