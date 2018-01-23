package com.maakservicess.beingparents.app_monitor.controllers;

/**
 * Created by SAI PC on 1/26/2016.
 */
import java.util.ArrayList;

public class VaccineGroupItem {

    private String Name;
    private ArrayList<VaccineChildItem> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<VaccineChildItem> getItems() {
        return Items;
    }

    public void setItems(ArrayList<VaccineChildItem> Items) {
        this.Items = Items;
    }

}