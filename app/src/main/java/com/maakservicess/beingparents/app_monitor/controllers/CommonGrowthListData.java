package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by Amey on 5/1/2016.
 */
public class CommonGrowthListData {

    private String record_id;
    private String enterDate;
    private String enterData;
    private float calculateDiff;
    private int counteLoop;
    private String category;

    public CommonGrowthListData(String record_id, String enterDate, String enterData, float calculateDiff, int counteLoop, String category) {
        this.record_id = record_id;
        this.enterDate = enterDate;
        this.enterData = enterData;
        this.calculateDiff = calculateDiff;
        this.counteLoop = counteLoop;
        this.category = category;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getEnterData() {
        return enterData;
    }

    public void setEnterData(String enterData) {
        this.enterData = enterData;
    }

    public float getCalculateDiff() {
        return calculateDiff;
    }

    public void setCalculateDiff(float calculateDiff) {
        this.calculateDiff = calculateDiff;
    }

    public int getCounteLoop() {
        return counteLoop;
    }

    public void setCounteLoop(int counteLoop) {
        this.counteLoop = counteLoop;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
