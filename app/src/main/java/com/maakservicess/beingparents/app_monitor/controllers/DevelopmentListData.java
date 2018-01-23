package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by SAI PC on 3/5/2016.
 */
public class DevelopmentListData {

    String id;
    String status;
    String task;
    String taskDescription;
    String completedOn;
    String rangeTo;
    String rangeFrom;
    int imgId;

    public DevelopmentListData(String id, String task,String taskDescription, String rangeFrom,String rangeTo,String completedOn, int imgId, String status) {
        this.id = id;
        this.status = status;
        this.task = task;
        this.taskDescription = taskDescription;
        this.completedOn = completedOn;
        this.rangeTo = rangeTo;
        this.rangeFrom = rangeFrom;
        this.imgId = imgId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }

    public String getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(String rangeTo) {
        this.rangeTo = rangeTo;
    }

    public String getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(String rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
