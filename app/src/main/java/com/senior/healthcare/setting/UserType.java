package com.senior.healthcare.setting;

public enum UserType {
    WOMEN20UP, // MEN20UP + 자궁경부암 검진
    WOMEN40UP, // MEN40UP + 유방암 검진
    MEN20UP, // 기본 검진
    MEN40UP, // MEN20UP + 간암, 위암 검진
    WOMEN50UP, // WOMEN40UP + 대장암 검진
    MEN50UP // MEN40UP + 대장암 검진

}
