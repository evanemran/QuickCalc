package com.evanemran.calculator.models;

import com.evanemran.calculator.R;

import java.io.Serializable;

public enum UnitsMenu implements Serializable {

    LENGTH("Length", R.drawable.length),
    AREA("Area", R.drawable.area),
    VOLUME("Volume", R.drawable.volume),
    SPEED("Speed", R.drawable.speed),
    WEIGHT("Weight", R.drawable.weight),
    TEMPERATURE("Temperature", R.drawable.temperature),
    POWER("Power", R.drawable.power),
    PRESSURE("Pressure", R.drawable.pressure);

    private String title = "";
    private int image = 0;

    UnitsMenu(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
