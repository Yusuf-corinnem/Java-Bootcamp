package edu.school21.models;

import java.util.StringJoiner;

public class Car {
    private String brand;
    private String model;
    private double power;
    private double length;
    private double width;
    private double height;

    public Car() {
        this.brand = "Default brand";
        this.model = "Default model";
        this.length = 0;
        this.width = 0;
        this.height = 0;
        this.power = 0;
    }

    public Car(String brand, String model, double power, double length, double width, double height) {
        this.brand = brand;
        this.model = model;
        this.power = power;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public double growPower(double value) {
        this.power += value;
        return this.power;
    }

    public double reducePower(double value) {
        this.power -= value;
        return this.power;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("brand='" + brand + "'")
                .add("model='" + model + "'")
                .add("power=" + power)
                .add("length=" + length)
                .add("width=" + width)
                .add("height=" + height)
                .toString();
    }
}
