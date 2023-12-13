package com.senior.healthcare.unit;
import com.senior.healthcare.setting.ApplicationSetting;
import com.senior.healthcare.setting.UserType;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.List;

public class SetUserInfoTest extends TestCase {

    @Test
    public void testOnCreate(){
        int age = 40;
        String gender = "여성";
        String siDoNm = "서울특별시";
        String siDoCd = "11";
        String siGunGuNm = "종로구";
        String siGunGuCd = "110";

        ApplicationSetting applicationSetting = new ApplicationSetting();
        applicationSetting.setGender(gender);
        applicationSetting.setAge(age);
        applicationSetting.setCity(siDoNm);
        applicationSetting.setVillage(siGunGuNm);
        applicationSetting.setCityCode(siDoCd);
        applicationSetting.setVillageCode(siGunGuCd);
        UserType userType = applicationSetting.getUserType();

        assertEquals(gender,applicationSetting.getGender());
        assertEquals(siDoNm,applicationSetting.getCity());
        assertEquals(siDoCd,applicationSetting.getCityCode());
        assertEquals(siGunGuNm,applicationSetting.getVillage());
        assertEquals(siGunGuCd,applicationSetting.getVillageCode());
    }

    public ApplicationSetting testOnComponent(int age, String gender, String siDoNm, String siGunGuNm, String siDoCd, String siGunGuCd){
        ApplicationSetting applicationSetting = new ApplicationSetting();
        applicationSetting.setGender(gender);
        applicationSetting.setAge(age);
        applicationSetting.setCity(siDoNm);
        applicationSetting.setVillage(siGunGuNm);
        applicationSetting.setCityCode(siDoCd);
        applicationSetting.setVillageCode(siGunGuCd);
        UserType userType = applicationSetting.getUserType();

        HealthCheckListTest healthCheckListTest = new HealthCheckListTest();
        List<String> canList = healthCheckListTest.testGetHealthCheckType(userType);

        return applicationSetting;
    }
}