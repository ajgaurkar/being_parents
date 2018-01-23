package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by SAI PC on 4/3/2016.
 */
public class MilestoneListData {

    int mileAge;
    String mileMotorDev;
    String mileAdaptive;
    String mileLanguage;
    String mileSocial;
    String mileVisionHearing;
    Boolean expandStatus;
    Boolean mileCurrentRangeStatus;

    public MilestoneListData(int mileAge, String mileMotorDev, String mileAdaptive, String mileLanguage, String mileSocial, String mileVisionHearing,Boolean mileCurrentRangeStatus, Boolean expandStatus) {
        this.mileAge = mileAge;
        this.mileMotorDev = mileMotorDev;
        this.mileAdaptive = mileAdaptive;
        this.mileLanguage = mileLanguage;
        this.mileSocial = mileSocial;
        this.mileVisionHearing = mileVisionHearing;
        this.mileCurrentRangeStatus = mileCurrentRangeStatus;
        this.expandStatus = expandStatus;
    }

    public int getMileAge() {
        return mileAge;
    }

    public void setMileAge(int mileAge) {
        this.mileAge = mileAge;
    }

    public String getMileMotorDev() {
        return mileMotorDev;
    }

    public void setMileMotorDev(String mileMotorDev) {
        this.mileMotorDev = mileMotorDev;
    }

    public String getMileAdaptive() {
        return mileAdaptive;
    }

    public void setMileAdaptive(String mileAdaptive) {
        this.mileAdaptive = mileAdaptive;
    }

    public String getMileLanguage() {
        return mileLanguage;
    }

    public void setMileLanguage(String mileLanguage) {
        this.mileLanguage = mileLanguage;
    }

    public String getMileSocial() {
        return mileSocial;
    }

    public void setMileSocial(String mileSocial) {
        this.mileSocial = mileSocial;
    }

    public String getMileVisionHearing() {
        return mileVisionHearing;
    }

    public void setMileVisionHearing(String mileVisionHearing) {
        this.mileVisionHearing = mileVisionHearing;
    }

    public Boolean getMileCurrentRangeStatus() {
        return mileCurrentRangeStatus;
    }

    public void setMileCurrentRangeStatus(Boolean mileCurrentRangeStatus) {
        this.mileCurrentRangeStatus = mileCurrentRangeStatus;
    }

    public Boolean getExpandStatus() {
        return expandStatus;
    }

    public void setExpandStatus(Boolean expandStatus) {
        this.expandStatus = expandStatus;
    }
}
