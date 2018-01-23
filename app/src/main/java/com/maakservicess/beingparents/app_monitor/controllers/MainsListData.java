package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by SAI PC on 3/27/2016.
 */
public class MainsListData {

    String id;
    String title;
    int imgId;

    public MainsListData(String id, String title, int imgId) {
        this.id = id;
        this.title = title;
        this.imgId = imgId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
