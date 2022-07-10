package com.example.biggestpicture.entity;


import java.io.Serializable;

public class Photo implements Serializable {
    int id;
    int sol;
    String img_src;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", sol=" + sol +
                ", img_src='" + img_src + '\'' +
                '}';
    }
}
