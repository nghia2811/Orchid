package com.project2.orchid.object;

public class Comment {
    String comment, id, tenkhachhang;

    public Comment() {
    }

    public Comment(String comment, String id, String tenkhachhang) {
        this.comment = comment;
        this.id = id;
        this.tenkhachhang = tenkhachhang;
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
