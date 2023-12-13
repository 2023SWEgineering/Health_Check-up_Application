package com.senior.healthcare.component;

import com.senior.healthcare.HospitalInfo;
import com.senior.healthcare.setting.ApplicationSetting;
import com.senior.healthcare.unit.HospitalSpecTest;
import com.senior.healthcare.unit.SearchByLocationTest;
import com.senior.healthcare.unit.SearchByUserInfoTest;
import com.senior.healthcare.unit.SiDoTest;
import com.senior.healthcare.unit.SiGunGuTest;

import junit.framework.TestCase;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class FindUserHospitalComponent extends TestCase {

    @Test
    public void testOnCreate() throws XmlPullParserException, IOException {
        SiDoTest siDoTest = new SiDoTest();
        String siDo = siDoTest.testOnComponent().get(0).getSidoCd();

        SiGunGuTest siGunGuTest = new SiGunGuTest();
        String siGunGu = siGunGuTest.testOnComponent().get(0).getSigunguCd();

        SearchByUserInfoTest searchByUserInfoTest = new SearchByUserInfoTest();

        String checkType = "간암";
        List<HospitalInfo> hospitalInfos = searchByUserInfoTest.testOnComponent(checkType, siDo, siGunGu);
        String hmcNm = hospitalInfos.get(0).getHospitalName();

        HospitalSpecTest hospitalSpecTest = new HospitalSpecTest();
        String expected = hospitalSpecTest.testOnComponent(hmcNm, siDo, siGunGu);
        assertEquals(expected,"02-2001-2001");
    }

    @Test
    public String testOnSystem(ApplicationSetting applicationSetting,String checkType) throws XmlPullParserException, IOException {
        SiDoTest siDoTest = new SiDoTest();
        String siDo = siDoTest.testOnComponent().get(0).getSidoCd();

        SiGunGuTest siGunGuTest = new SiGunGuTest();
        String siGunGu = siGunGuTest.testOnComponent().get(0).getSigunguCd();

        SearchByUserInfoTest searchByUserInfoTest = new SearchByUserInfoTest();

        List<HospitalInfo> hospitalInfos = searchByUserInfoTest.testOnComponent(checkType, applicationSetting.getCityCode(),applicationSetting.getVillageCode());
        String hmcNm = hospitalInfos.get(0).getHospitalName();

        HospitalSpecTest hospitalSpecTest = new HospitalSpecTest();
        String expected = hospitalSpecTest.testOnComponent(hmcNm, applicationSetting.getCityCode(), applicationSetting.getVillageCode());
        return expected;
    }
}
