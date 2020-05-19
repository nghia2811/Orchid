package com.project2.orchid.Object;

public class Notification {
    private String Title, Product, Customer, Image;

    public Notification() {
    }

    public Notification(String title, String product, String customer, String image) {
        Title = title;
        Product = product;
        Customer = customer;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
