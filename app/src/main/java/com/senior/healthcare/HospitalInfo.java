package com.senior.healthcare;

public class HospitalInfo {
    private String hospitalName;
    private String hospitalCode;
    private boolean bcExmdChrgTypeCd;
    private boolean ccExmdChrgTypeCd;
    private boolean cvxcaExmdChrgTypeCd;
    private boolean grenChrgTypeCd;
    private boolean lvcaExmdChrgTypeCd;
    private boolean stmcaExmdChrgTypeCd;

    private boolean mchkChrgTypeCd;//구강


    public HospitalInfo(){
        bcExmdChrgTypeCd = false;
        ccExmdChrgTypeCd = false;
        cvxcaExmdChrgTypeCd = false;
        grenChrgTypeCd = false;
        lvcaExmdChrgTypeCd = false;
        stmcaExmdChrgTypeCd = false;
        mchkChrgTypeCd = false;
    }
    public boolean isBcExmdChrgTypeCd() {
        return bcExmdChrgTypeCd;
    }

    public void setBcExmdChrgTypeCd(boolean bcExmdChrgTypeCd) {
        this.bcExmdChrgTypeCd = bcExmdChrgTypeCd;
    }

    public boolean isMchkChrgTypeCd() {
        return mchkChrgTypeCd;
    }

    public void setMchkChrgTypeCd(boolean mchkChrgTypeCd) {
        this.mchkChrgTypeCd = mchkChrgTypeCd;
    }
    public boolean isCcExmdChrgTypeCd() {
        return ccExmdChrgTypeCd;
    }

    public void setCcExmdChrgTypeCd(boolean ccExmdChrgTypeCd) {
        this.ccExmdChrgTypeCd = ccExmdChrgTypeCd;
    }

    public boolean isCvxcaExmdChrgTypeCd() {
        return cvxcaExmdChrgTypeCd;
    }

    public void setCvxcaExmdChrgTypeCd(boolean cvxcaExmdChrgTypeCd) {
        this.cvxcaExmdChrgTypeCd = cvxcaExmdChrgTypeCd;
    }

    public boolean isGrenChrgTypeCd() {
        return grenChrgTypeCd;
    }

    public void setGrenChrgTypeCd(boolean grenChrgTypeCd) {
        this.grenChrgTypeCd = grenChrgTypeCd;
    }

    public boolean isLvcaExmdChrgTypeCd() {
        return lvcaExmdChrgTypeCd;
    }

    public void setLvcaExmdChrgTypeCd(boolean lvcaExmdChrgTypeCd) {
        this.lvcaExmdChrgTypeCd = lvcaExmdChrgTypeCd;
    }

    public boolean isStmcaExmdChrgTypeCd() {
        return stmcaExmdChrgTypeCd;
    }

    public void setStmcaExmdChrgTypeCd(boolean stmcaExmdChrgTypeCd) {
        this.stmcaExmdChrgTypeCd = stmcaExmdChrgTypeCd;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
}
