package com.example.biggestpicture.entity;

import java.io.Serializable;
import java.util.Arrays;

public class Photos implements Serializable {
    Photo[] photos;

    public Photo[] getPhotos() {
        return photos;
    }

    public void setPhotos(Photo[] photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Photos{" +
                "photos=" + Arrays.toString(photos) +
                '}';
    }
}
