package com.senior.healthcare.component;

import com.senior.healthcare.setting.ApplicationSetting;
import com.senior.healthcare.unit.HealthCheckListTest;
import com.senior.healthcare.unit.SetUserInfoTest;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
public class FindUserCheckListComponent extends TestCase {

    @Test
    public void testOnCreate() {
        int age = 45;
        String gender = "여성";
        String siDoNm = "서울특별시";
        String siDoCd = "11";
        String siGunGuNm = "종로구";
        String siGunGuCd = "110";

        SetUserInfoTest setUserInfoTest = new SetUserInfoTest();
        ApplicationSetting applicationSetting = setUserInfoTest.testOnComponent(age, gender, siDoNm, siGunGuNm, siDoCd, siGunGuCd);


        HealthCheckListTest healthCheckListTest = new HealthCheckListTest();
        List<String> canList = healthCheckListTest.testGetHealthCheckType(applicationSetting.getUserType());

        List<String> expected = new ArrayList<>();
        expected.add("일반");
        expected.add("구강");
        expected.add("간암");
        expected.add("위암");

        assertEquals(expected, canList);
    }

    public List<String> testOnSystem(ApplicationSetting applicationSetting) {

        HealthCheckListTest healthCheckListTest = new HealthCheckListTest();
        List<String> canList = healthCheckListTest.testGetHealthCheckType(applicationSetting.getUserType());

        return canList;
    }
}