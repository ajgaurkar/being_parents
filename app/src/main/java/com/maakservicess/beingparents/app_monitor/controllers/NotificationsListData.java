package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by SAI PC on 3/5/2016.
 */
public class NotificationsListData {

    int id;
    String notificationHeader;
    String notificationDescription;
    int imgId;
    int readStatus;
    int showStatus;
    Long notifyTimeStamp;

    public NotificationsListData(int id, String notificationHeader, String notificationDescription, int imgId, int readStatus, int showStatus, Long notifyTimeStamp) {
        this.id = id;
        this.notificationHeader = notificationHeader;
        this.notificationDescription = notificationDescription;
        this.imgId = imgId;
        this.readStatus = readStatus;
        this.showStatus = showStatus;
        this.notifyTimeStamp = notifyTimeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotificationHeader() {
        return notificationHeader;
    }

    public void setNotificationHeader(String notificationHeader) {
        this.notificationHeader = notificationHeader;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public void setNotificationDescription(String notificationDescription) {
        this.notificationDescription = notificationDescription;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public int getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }

    public Long getNotifyTimeStamp() {
        return notifyTimeStamp;
    }

    public void setNotifyTimeStamp(Long notifyTimeStamp) {
        this.notifyTimeStamp = notifyTimeStamp;
    }
}
