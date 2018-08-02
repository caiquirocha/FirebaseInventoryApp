package com.myapplicationdev.android.demodialog.firebaseinventoryapp;

import java.io.Serializable;

public class Inventory implements Serializable{
    private String id;
    private String brand;
    private double unit_cost;

    public Inventory(){
    }

    public Inventory(String brand, double unit_price) {
        this.brand = brand;
        this.unit_cost = unit_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getUnit_price() {
        return unit_cost;
    }

    public void setUnit_price(double unit_price) {
        this.unit_cost = unit_price;
    }

    @Override
    public String toString() {
        return brand;
    }
}
