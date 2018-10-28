package com.ali.memco.evolaptop.dataModel;

import android.graphics.drawable.Drawable;

public class Cloth {

    private int id;
    private Drawable clothImg;
    private String clothTitle;
    private int countView;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getClothImg() {
        return clothImg;
    }

    public void setClothImg(Drawable clothImg) {
        this.clothImg = clothImg;
    }

    public String getClothTitle() {
        return clothTitle;
    }

    public void setClothTitle(String clothTitle) {
        this.clothTitle = clothTitle;
    }

    public int getCountView() {
        return countView;
    }

    public void setCountView(int countView) {
        this.countView = countView;
    }
}
