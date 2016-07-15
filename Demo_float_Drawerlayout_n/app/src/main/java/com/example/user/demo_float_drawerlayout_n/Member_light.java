package com.example.user.demo_float_drawerlayout_n;

public class Member_light { // VO- Value Object
    private int id;
    private int image;
    private String name;
    private String title;
    private String id1;
    private String id2;

    public Member_light() {
        super();
    }

    public Member_light(int id, int image, String name,String title,String id1,String id2) {
        super();
        this.id = id;
        this.image = image;
        this.name = name;
        this.id1=id1;
        this.id2=id2;
        this.title=title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String gettitle() {
        return title;
    }
    public String getid1() {
        return id1;
    }
    public String getid2() {
        return id2;
    }
}
