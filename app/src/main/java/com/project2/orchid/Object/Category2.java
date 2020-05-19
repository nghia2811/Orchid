package com.project2.orchid.Object;

public class Category2 {
    private String Title;
    private int Thumbnail;

    public Category2(){
    }

    public Category2(String title, int thumbnail){
        Title = title;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
