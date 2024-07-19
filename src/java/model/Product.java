/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class Product {
    private int id, quantity, brand_id;
    private String name, description, image_url;
    private double price;
    private Date release_date;

    public Product() {
    }

    //constructor for insert a new product
    public Product(int quantity, int brand_id, String name, String description, String image_url, double price, Date release_date) {
        this.quantity = quantity;
        this.brand_id = brand_id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.release_date = release_date;
    }
    public Product(int id, int quantity, int brand_id, String name, String description, String image_url, double price, Date release_date) {
        this.id = id;
        this.quantity = quantity;
        this.brand_id = brand_id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }
    
    
}
