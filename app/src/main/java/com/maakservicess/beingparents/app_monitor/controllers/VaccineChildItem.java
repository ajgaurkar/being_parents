package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by SAI PC on 1/26/2016.
 */

public class VaccineChildItem {

    private String vaccineId;
    private String vaccineGroup;
    private String Name;
    private String duedt;
    private String takendate;
    private String vaccineStatus;
    private int Image;

    public String getVaccineGroup() {
        return vaccineGroup;
    }

    public void setVaccineGroup(String vaccineGroup) {
        this.vaccineGroup = vaccineGroup;
    }

    public String getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(String vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getVaccineStatus() {
        return vaccineStatus;
    }

    public void setVaccineStatus(String vaccineStatus) {
        this.vaccineStatus = vaccineStatus;
    }

    public String getDuedt() {
        return duedt;
    }

    public void setDuedt(String duedt) {
        this.duedt = duedt;
    }

    public String getTakendate() {
        return takendate;
    }

    public void setTakendate(String takendate) {
        this.takendate = takendate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int Image) {
        this.Image = Image;
    }
}