package edu.school21.models;

public class Product {
    private long id;
    private String name;
    private float price;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public Product(long id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
