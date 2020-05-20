package com.project2.orchid.Object;

public class Comment {
    String comment, id, tenkhachhang, image;

    public Comment() {
    }

    public Comment(String comment, String id, String tenkhachhang, String image) {
        this.comment = comment;
        this.id = id;
        this.tenkhachhang = tenkhachhang;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }
}
