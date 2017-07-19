package com.kiven.sample.annotation;

/**
 * Created by Kiven on 2017/7/4.
 * Details:
 */

public class Apple {
    @FruitName("苹果")
    private String name;
    @FruitColor(fruitColor = FruitColor.Color.RED)
    private String appleColor;
    @FruitProvider(id = 1, name = "red ltc.", address = "AnJiang")
    private String appleProvider;

    public String getName() {
        return name;
    }

    public String getAppleProvider() {
        return appleProvider;
    }

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppleColor() {
        return appleColor;
    }

    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }

    public void disPlayName() {
        System.out.println("Fruit Name = " + getName());
    }
}
