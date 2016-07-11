package com.hmmelton.tangential.models;

/**
 * Created by harrisonmelton on 7/10/16.
 * This is a model used to store a stock quote, as well as a color (green or red) to represent
 * whether or not an asset has increased or decreased in value;
 */
public class StyledQuote {
    private double value;
    private int color;

    public StyledQuote(double value, int color) {
        this.value = value;
        this.color = color;
    }

    public double getValue() {
        return value;
    }

    public int getColor() {
        return color;
    }
}
