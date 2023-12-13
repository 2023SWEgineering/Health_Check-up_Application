package com.senior.healthcare.component;

import static com.senior.healthcare.setting.UserType.MEN20UP;

import com.senior.healthcare.setting.ApplicationSetting;
import com.senior.healthcare.setting.UserType;

import junit.framework.TestCase;

import org.junit.Test;

public class ApplicationSettingComponent extends TestCase {

    ApplicationSetting applicationSetting = new ApplicationSetting();
    @Test
    public void testGetAge() {
        int expected = 30;
        applicationSetting.setAge(expected);
        assertEquals(expected,applicationSetting.getAge());
    }

    @Test
    public void testSetAge() {
        int expected = 30;
        applicationSetting.setAge(expected);
        assertEquals(expected,applicationSetting.getAge());
    }

    public void testGetGender() {
        String expected = "여성";
        applicationSetting.setGender(expected);
        assertEquals(expected,applicationSetting.getGender());
    }

    public void testSetGender() {
        String expected = "여성";
        applicationSetting.setGender(expected);
        assertEquals(expected,applicationSetting.getGender());
    }

    public void testGetCity() {
        String expected = "서울특별시";
        applicationSetting.setCity(expected);
        assertEquals(expected,applicationSetting.getCity());
    }

    public void testSetCity() {
        String expected = "서울특별시";
        applicationSetting.setCity(expected);
        assertEquals(expected,applicationSetting.getCity());
    }

    public void testGetCityCode() {
        String expected = "11";
        applicationSetting.setCityCode(expected);
        assertEquals(expected,applicationSetting.getCityCode());
    }

    public void testSetCityCode() {
        String expected = "11";
        applicationSetting.setCityCode(expected);
        assertEquals(expected,applicationSetting.getCityCode());
    }

    public void testGetVillage() {
        String expected = "종로구";
        applicationSetting.setVillage(expected);
        assertEquals(expected,applicationSetting.getVillage());
    }

    public void testSetVillage() {
        String expected = "종로구";
        applicationSetting.setVillage(expected);
        assertEquals(expected,applicationSetting.getVillage());
    }

    public void testGetVillageCode() {
        String expected = "110";
        applicationSetting.setVillageCode(expected);
        assertEquals(expected,applicationSetting.getVillageCode());
    }

    public void testSetVillageCode() {
        String expected = "종로구";
        applicationSetting.setVillage(expected);
        assertEquals(expected,applicationSetting.getVillage());
    }

    public void testGetServiceKey() {
        String expected = "eOIFq%2FdyWqgEH46rcHoOaXBRiKvn9XwDy8I2ISFT5dBWNSPgI%2BCty58H1nxLT81K0UL2zPVwIh40FNfXlBTsRg%3D%3D";
        assertEquals(expected,applicationSetting.getServiceKey());
    }

    public void testSetHospitalName() {
        String expected = "강북삼성병원";
        applicationSetting.setHospitalName(expected);
        assertEquals(expected,applicationSetting.getHospitalName());
    }

    public void testSetHospitalCode() {
        String expected = "11100095";
        applicationSetting.setHospitalCode(expected);
        assertEquals(expected,applicationSetting.getHospitalCode());
    }

    public void testGetHospitalName() {
        String expected = "강북삼성병원";
        applicationSetting.setHospitalName(expected);
        assertEquals(expected,applicationSetting.getHospitalName());
    }

    public void testGetHospitalCode() {
        String expected = "11100095";
        applicationSetting.setHospitalCode(expected);
        assertEquals(expected,applicationSetting.getHospitalCode());
    }

    public void testGetUserType() {
        UserType expected = MEN20UP;
        applicationSetting.setAge(20);
        applicationSetting.setGender("남자");
        assertEquals(expected,applicationSetting.getUserType());
    }
}