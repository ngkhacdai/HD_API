package com.example.hd_api.models;

public class Product {
    private String _id;
    private String name ;
    private String description ;
    private int price ;
    private String category  ;
    private String image  ;
    private int stockQuantity  ;

    public Product(String _id, String name, String description, int price, String category, String image, int stockQuantity) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.stockQuantity = stockQuantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
