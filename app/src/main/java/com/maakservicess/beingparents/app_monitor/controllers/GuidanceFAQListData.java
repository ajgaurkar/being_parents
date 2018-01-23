package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by amey on 1/2/2017.
 */
public class GuidanceFAQListData {
    private int questionNumber;
    private String guidanceQuestions;
    private String guidanceAnswers;
    private Boolean expandStatus;

    public GuidanceFAQListData(int questionNumber, String guidanceQuestions, String guidanceAnswers, Boolean expandStatus) {
        this.questionNumber = questionNumber;
        this.guidanceQuestions = guidanceQuestions;
        this.guidanceAnswers = guidanceAnswers;
        this.expandStatus = expandStatus;
    }

    public String getGuidanceQuestions() {
        return guidanceQuestions;
    }

    public void setGuidanceQuestions(String guidanceQuestions) {
        this.guidanceQuestions = guidanceQuestions;
    }

    public String getGuidanceAnswers() {
        return guidanceAnswers;
    }

    public void setGuidanceAnswers(String guidanceAnswers) {
        this.guidanceAnswers = guidanceAnswers;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Boolean getExpandStatus() {
        return expandStatus;
    }

    public void setExpandStatus(Boolean expandStatus) {
        this.expandStatus = expandStatus;
    }
}
