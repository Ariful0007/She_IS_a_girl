package com.example.she_is_a_girl;

public class ModelUser {
    String  name,email,search,number,image,coverImage,uid;

    public ModelUser() {
    }

    public ModelUser(String name, String email, String search, String number, String image, String coverImage, String uid) {
        this.name = name;
        this.email = email;
        this.search = search;
        this.number = number;
        this.image = image;
        this.coverImage = coverImage;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSearch() {
        return search;
    }

    public String getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public String getUid() {
        return uid;
    }
}
