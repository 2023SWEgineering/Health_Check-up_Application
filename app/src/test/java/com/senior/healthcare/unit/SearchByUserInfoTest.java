package com.senior.healthcare.unit;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.senior.healthcare.HospitalInfo;
import com.senior.healthcare.R;
import com.senior.healthcare.setting.ApplicationSetting;
import com.senior.healthcare.setting.UserType;

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

public class SearchByUserInfoTest extends TestCase {

    private static boolean isParsingDone;

    @Test
    public void testOnCreate() throws IOException, XmlPullParserException {
        String sidoCd = "11";
        String siGunGu = "110";
        String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?siDoCd=" + sidoCd + "&siGunGuCd=" + siGunGu + "&numOfRows=300&ServiceKey=" + ApplicationSetting.getServiceKey();
        String checkType = "간암";

        String xmlData = getXmlFromUrl(API_URL);
        List<HospitalInfo> hospitalInfos = parseXml(xmlData,checkType);

        boolean expected = true;
        assertEquals(expected,hospitalInfos.get(0).isLvcaExmdChrgTypeCd());
    }

    public List<HospitalInfo> testOnComponent(String checkType, String sidoCd, String siGunGu) throws IOException, XmlPullParserException {
        String API_URL = "http://openapi1.nhis.or.kr/openapi/service/rest/HmcSearchService/getRegnHmcList?siDoCd=" + sidoCd + "&siGunGuCd=" + siGunGu + "&numOfRows=300&ServiceKey=" + ApplicationSetting.getServiceKey();

        String xmlData = getXmlFromUrl(API_URL);
        List<HospitalInfo> hospitalInfos = parseXml(xmlData,checkType);

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

    private List<HospitalInfo> parseXml(String xmlData, String checkType) throws XmlPullParserException, IOException {
        List<HospitalInfo> hospitalList = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new java.io.StringReader(xmlData));

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            HospitalInfo hospitalInfo = new HospitalInfo();
            if (eventType == XmlPullParser.START_TAG && "item".equals(parser.getName())) {
                while (eventType != XmlPullParser.END_TAG || !"item".equals(parser.getName())) {
                    if (eventType == XmlPullParser.START_TAG) {
                        switch (parser.getName()) {
                            case "hmcNm":
                                hospitalInfo.setHospitalName(parser.nextText());
                                break;
                            case "hmcNo":
                                hospitalInfo.setHospitalCode(parser.nextText());
                                break;
                            case "hmcTelNo":
                                hospitalInfo.setHospitalCode( parser.nextText());
                                break;
                            case "bcExmdChrgTypeCd"://유방암
                                hospitalInfo.setBcExmdChrgTypeCd("1".equals(parser.nextText()));
                                break;
                            case "ccExmdChrgTypeCd"://대장암
                                hospitalInfo.setCcExmdChrgTypeCd("1".equals(parser.nextText()));
                                break;
                            case "cvxcaExmdChrgTypeCd"://자궁경부암
                                hospitalInfo.setCvxcaExmdChrgTypeCd("1".equals(parser.nextText()));
                                break;
                            case "grenChrgTypeCd"://일반 검진
                                hospitalInfo.setGrenChrgTypeCd("1".equals(parser.nextText()));
                                break;
                            case "lvcaExmdChrgTypeCd"://간암 검진
                                hospitalInfo.setLvcaExmdChrgTypeCd("1".equals(parser.nextText()));
                                break;
                            case "mchkChrgTypeCd"://구강 검진
                                hospitalInfo.setMchkChrgTypeCd("1".equals(parser.nextText()));
                                break;
                            case "stmcaExmdChrgTypeCd"://위암 검진
                                hospitalInfo.setStmcaExmdChrgTypeCd("1".equals(parser.nextText()));
                                break;
                        }
                    }
                    eventType = parser.next();
                }//파싱 종료
            }
            eventType = parser.next();
            boolean isCanAdd = checkHospitalByCheckType(checkType, hospitalInfo);
            if(isCanAdd)hospitalList.add(hospitalInfo);
        }
        isParsingDone = true;
        return hospitalList;
    }

    private boolean checkHospitalByCheckType(String checkType, HospitalInfo hospitalInfo) {
        if (checkType.equals("일반")) {
            if (hospitalInfo.isGrenChrgTypeCd()) {
                return true;
            }
            return false;
        } else if (checkType.equals("구강")) {
            if(hospitalInfo.isMchkChrgTypeCd())return true;
            return false;
        } else if (checkType.equals("자궁경부암")) {
            if (hospitalInfo.isCvxcaExmdChrgTypeCd())
                return true;
            return false;
        } else if (checkType.equals("위암")) {
            if (hospitalInfo.isStmcaExmdChrgTypeCd()) return true;
            return false;
        } else if (checkType.equals("간암")) {
            if (hospitalInfo.isLvcaExmdChrgTypeCd()) return true;
            return false;
        }
        else if(checkType.equals("유방암")){
            if (hospitalInfo.isBcExmdChrgTypeCd())return true;
            return false;
        }
        else if(checkType.equals("대장암")){
            if (hospitalInfo.isCcExmdChrgTypeCd())return true;
            return false;
        }
        else if(checkType.equals("폐암")){
            if (hospitalInfo.isLvcaExmdChrgTypeCd() && hospitalInfo.isStmcaExmdChrgTypeCd()
                    && hospitalInfo.isCcExmdChrgTypeCd()){
                return true;
            }
            return false;
        }
        return false;
    }
}