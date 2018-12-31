package com.litchiny.dailylife.mode;

/**
 * Created by ll on 2018/8/12.
 */

public class TestBean  {
    public String text;
    public boolean draggable = true;

    public TestBean(String text, boolean draggable) {
        this.text = text;
        this.draggable = draggable;
    }

    public TestBean(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "TestBean{" +
                "text='" + text + '\'' +
                ", draggable=" + draggable +
                '}';
    }
}
