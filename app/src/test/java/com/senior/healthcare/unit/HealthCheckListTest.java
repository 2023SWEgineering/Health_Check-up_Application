package com.senior.healthcare.unit;

import com.senior.healthcare.setting.UserType;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HealthCheckListTest extends TestCase {

    @Test
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

        List<String> expected = new ArrayList<>();
        expected.add("일반");
        expected.add("구강");
        expected.add("자궁경부암");

        assertEquals(expected,canList);
    }

    public List<String> testGetHealthCheckType(UserType userType) {
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
        return canList;
    }
}