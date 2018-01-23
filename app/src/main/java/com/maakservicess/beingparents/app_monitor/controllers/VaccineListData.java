package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by SAI PC on 1/18/2016.
 */
public class VaccineListData {

    String vaccineName;
    String vaccineStatus;
    String vaccineDueDate;
    String vaccineMissedDate;
    String vaccineTakenDate;
    String vaccineText;
    int nullId;

    public VaccineListData(String vaccineName, String vaccineStatus, String vaccineDueDate, String vaccineMissedDate, String vaccineTakenDate, String vaccineText) {
        this.vaccineName = vaccineName;
        this.vaccineStatus = vaccineStatus;
        this.vaccineDueDate = vaccineDueDate;
        this.vaccineMissedDate = vaccineMissedDate;
        this.vaccineTakenDate = vaccineTakenDate;
        this.vaccineText = vaccineText;
    }

    public int getNullId() {
        return nullId;
    }

    public void setNullId(int nullId) {
        this.nullId = nullId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getVaccineDueDate() {
        return vaccineDueDate;
    }

    public void setVaccineDueDate(String vaccineDueDate) {
        this.vaccineDueDate = vaccineDueDate;
    }

    public String getVaccineMissedDate() {
        return vaccineMissedDate;
    }

    public void setVaccineMissedDate(String vaccineMissedDate) {
        this.vaccineMissedDate = vaccineMissedDate;
    }

    public String getVaccineTakenDate() {
        return vaccineTakenDate;
    }

    public void setVaccineTakenDate(String vaccineTakenDate) {
        this.vaccineTakenDate = vaccineTakenDate;
    }

    public String getVaccineText() {
        return vaccineText;
    }

    public void setVaccineText(String vaccineText) {
        this.vaccineText = vaccineText;
    }

    public String getVaccineStatus() {
        return vaccineStatus;
    }

    public void setVaccineStatus(String vaccineStatus) {
        this.vaccineStatus = vaccineStatus;
    }
}
