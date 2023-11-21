package com.senior.healthcare.setting;

public class ApplicationSetting {
    private static int age;
    private static String name;
    private static String gender;
    private static String city;
    private static String cityCode;
    private static String village;
    private static String villageCode;
    private static String hospitalName;
    private static String hospitalCode;
    private static String serviceKey = "eOIFq%2FdyWqgEH46rcHoOaXBRiKvn9XwDy8I2ISFT5dBWNSPgI%2BCty58H1nxLT81K0UL2zPVwIh40FNfXlBTsRg%3D%3D";

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        ApplicationSetting.age = age;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        ApplicationSetting.name = name;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        ApplicationSetting.gender = gender;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        ApplicationSetting.city = city;
    }

    public static String getCityCode() {
        return cityCode;
    }

    public static void setCityCode(String cityCode) {
        ApplicationSetting.cityCode = cityCode;
    }

    public static String getVillage() {
        return village;
    }

    public static void setVillage(String village) {
        ApplicationSetting.village = village;
    }

    public static String getVillageCode() {
        return villageCode;
    }

    public static void setVillageCode(String villageCode) {
        ApplicationSetting.villageCode = villageCode;
    }
    public static String getServiceKey() {
        return serviceKey;
    }

    public static void setHospitalName(String hospitalName) {
        ApplicationSetting.hospitalName = hospitalName;
    }

    public static void setHospitalCode(String hospitalCode) {
        ApplicationSetting.hospitalCode = hospitalCode;
    }

    public static String getHospitalName() {
        return hospitalName;
    }

    public static String getHospitalCode() {
        return hospitalCode;
    }
}
