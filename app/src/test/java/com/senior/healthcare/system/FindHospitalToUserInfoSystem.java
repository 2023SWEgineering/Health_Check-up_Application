package com.senior.healthcare.system;

import com.senior.healthcare.component.FindNearHospitalComponent;
import com.senior.healthcare.component.FindUserCheckListComponent;
import com.senior.healthcare.component.FindUserHospitalComponent;
import com.senior.healthcare.unit.SetUserInfoTest;
import com.senior.healthcare.setting.ApplicationSetting;

import junit.framework.TestCase;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
public class FindHospitalToUserInfoSystem extends TestCase {

    private int age = 45;
    private String gender = "여성";

    @Test
    public void testOnCreate() throws XmlPullParserException, IOException {

        int age = 45;
        String gender = "여성";
        String siDoCd = "11";
        String siDoNm = "서울특별시";
        String siGunGuCd = "110";
        String siGunGuNm = "종로구";

        SetUserInfoTest setUserInfoTest = new SetUserInfoTest();
        ApplicationSetting applicationSetting = setUserInfoTest.testOnComponent(age, gender, siDoNm, siGunGuNm, siDoCd, siGunGuCd);

        FindUserCheckListComponent findUserCheckListComponent = new FindUserCheckListComponent();
        List<String> canlist = findUserCheckListComponent.testOnSystem(applicationSetting);

        String checkType = canlist.get(0);

        FindUserHospitalComponent findUserHospitalComponent = new FindUserHospitalComponent();
        String expected = findUserHospitalComponent.testOnSystem(applicationSetting,checkType);
        assertEquals(expected,"02-2002-8000");
    }
}