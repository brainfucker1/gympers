package com.example.gympers.models;

import java.io.Serializable;

public class BrandsCategory implements Serializable {
    String name;
    String img_url;
    String description;
    String price;
    String type;

    public BrandsCategory() {
    }

    public BrandsCategory(String name, String img_url, String description, String price, String type) {
        this.name = name;
        this.img_url = img_url;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
