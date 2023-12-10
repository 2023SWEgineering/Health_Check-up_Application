package com.senior.healthcare.setting;

public class ApplicationSetting {
    private static Integer age;
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

    public static void setAge(Integer age) {
        ApplicationSetting.age = age;
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
    public static UserType getUserType(){
        if (age >= 20 && age < 40){
            if (gender.equals("여자"))return UserType.WOMEN20UP;//20~30대인 경우
            return UserType.MEN20UP;
        }
        else if (age >= 40 && age < 50){
            if (gender.equals("여자")){
                return UserType.WOMEN40UP; //40대인 경우
            }
            else return UserType.MEN40UP;
        }
        else if(age >= 50){
            if (gender.equals("여자"))return UserType.WOMEN50UP;// 50세 이상인 경우
            return UserType.MEN50UP;
        }
        else{
            return null;//잘못 입력된 경우
        }
    }
}
