package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by amey on 2/22/2016.
 */
public class VaccineSqliteData {

    private String id;
    private String group;
    private String vaccine;
    private String dueDate;
    private String takenDate;
    private String status;

    public VaccineSqliteData() {
    }

    public VaccineSqliteData(String id, String group, String vaccine, String dueDate, String takenDate, String status) {
        this.id = id;
        this.group = group;
        this.vaccine = vaccine;
        this.dueDate = dueDate;
        this.takenDate = takenDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(String takenDate) {
        this.takenDate = takenDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
