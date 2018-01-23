package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by SAI PC on 4/3/2016.
 */
public class CalendarEntryListData {

    int id;
    int entryDateYear;
    int entryDateMonth;
    int entryDateDay;
    String title;
    String description;
    String imgFirstLink;
    String imgSecondLink;
    String imgThirdLink;
    int imgCount;
    String category;


    public CalendarEntryListData(int id,
                                 int entryDateDay, int entryDateMonth, int entryDateYear, String title,
                                 String description,
                                 String imgFirstLink, String imgSecondLink, String imgThirdLink,
                                 int imgCount, String category) {
        this.id = id;
        this.entryDateYear = entryDateYear;
        this.entryDateMonth = entryDateMonth;
        this.entryDateDay = entryDateDay;
        this.title = title;
        this.description = description;
        this.imgFirstLink = imgFirstLink;
        this.imgSecondLink = imgSecondLink;
        this.imgThirdLink = imgThirdLink;
        this.imgCount = imgCount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntryDateYear() {
        return entryDateYear;
    }

    public void setEntryDateYear(int entryDateYear) {
        this.entryDateYear = entryDateYear;
    }

    public int getEntryDateMonth() {
        return entryDateMonth;
    }

    public void setEntryDateMonth(int entryDateMonth) {
        this.entryDateMonth = entryDateMonth;
    }

    public int getEntryDateDay() {
        return entryDateDay;
    }

    public void setEntryDateDay(int entryDateDay) {
        this.entryDateDay = entryDateDay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgFirstLink() {
        return imgFirstLink;
    }

    public void setImgFirstLink(String imgFirstLink) {
        this.imgFirstLink = imgFirstLink;
    }

    public String getImgSecondLink() {
        return imgSecondLink;
    }

    public void setImgSecondLink(String imgSecondLink) {
        this.imgSecondLink = imgSecondLink;
    }

    public String getImgThirdLink() {
        return imgThirdLink;
    }

    public void setImgThirdLink(String imgThirdLink) {
        this.imgThirdLink = imgThirdLink;
    }

    public int getImgCount() {
        return imgCount;
    }

    public void setImgCount(int imgCount) {
        this.imgCount = imgCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
