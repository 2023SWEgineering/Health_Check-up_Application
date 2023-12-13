package com.senior.healthcare.searching;

import com.senior.healthcare.setting.ApplicationSetting;
import com.senior.healthcare.setting.UserType;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HealthCheckListTest extends TestCase {

    public void testGetHealthCheckType() {
        UserType userType = UserType.valueOf("WOMEN20UP");
        List<String> canList = new LinkedList<>();
        switch (userType){
            case MEN20UP :
                canList.add("일반");
                canList.add("구강");
                break;
            case WOMEN20UP :
                canList.add("일반");
                canList.add("구강");
                canList.add("자궁경부암");
                break;
            case MEN40UP :
                canList.add("일반");
                canList.add("구강");
                canList.add("간암");
                canList.add("위암");
                break;
            case WOMEN40UP :
                canList.add("일반");
                canList.add("구강");
                canList.add("간암");
                canList.add("위암");
                canList.add("자궁경부암");
                canList.add("유방암");
                break;
            case MEN50UP :
                canList.add("일반");
                canList.add("구강");
                canList.add("간암");
                canList.add("위암");
                canList.add("대장암");
                break;
            case WOMEN50UP :
                canList.add("일반");
                canList.add("구강");
                canList.add("간암");
                canList.add("위암");
                canList.add("대장암");
                canList.add("자궁경부암");
                canList.add("유방암");
                break;
            case MENLASTGEN :
                canList.add("일반");
                canList.add("구강");
                canList.add("간암");
                canList.add("위암");
                canList.add("대장암");
                canList.add("폐암");
                break;
            case WOMENLASTGEN :
                canList.add("일반");
                canList.add("구강");
                canList.add("간암");
                canList.add("위암");
                canList.add("대장암");
                canList.add("폐암");
                canList.add("자궁경부암");
                canList.add("유방암");
                break;
        }
        System.out.println(canList);
    }
}