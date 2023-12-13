package com.senior.healthcare.system;

import com.senior.healthcare.component.FindNearHospitalComponent;
import com.senior.healthcare.component.FindUserCheckListComponent;
import com.senior.healthcare.component.FindUserHospitalComponent;
import com.senior.healthcare.setting.ApplicationSetting;
import com.senior.healthcare.unit.SetUserInfoTest;

import junit.framework.TestCase;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
public class FindHospitalToLocalInfoSystem extends TestCase {

    private int age = 45;
    private String gender = "여자";

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

        FindNearHospitalComponent findNearHospitalComponent = new FindNearHospitalComponent();
        String expected = findNearHospitalComponent.testOnSystem(applicationSetting);
        assertEquals(expected,"02-2001-2001");
    }
}
