package com.senior.healthcare.setting;

public class ApplicationSetting {
    private static int age;
    private static String name;
    private static String gender;
    private static String city;
    private static String cityCode;
    private static String village;
    private static int villageCode;
    private static String lastFind;

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

    public static int getVillageCode() {
        return villageCode;
    }

    public static void setVillageCode(int villageCode) {
        ApplicationSetting.villageCode = villageCode;
    }

    public static String getLastFind() {
        return lastFind;
    }

    public static void setLastFind(String lastFind) {
        ApplicationSetting.lastFind = lastFind;
    }
}
