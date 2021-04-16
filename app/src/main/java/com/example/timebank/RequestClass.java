package com.example.timebank;

public class RequestClass {
    private String id;
    private String user;
    private String title;
    private String desc;
    private String category;
    private boolean isOneTime;
    private String expirationDate;
    private String[] categories;

    public RequestClass() {
    }

    public RequestClass(String id, String user, String title, String desc, boolean isOneTime) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.desc = desc;
        this.isOneTime = isOneTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        if (user == null) {
            System.out.println("User is null");
        }
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            System.out.println("Title is null");
        }
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        if (desc == null) {
            System.out.println("Desc is null");
        }
        this.desc = desc;
    }

    public boolean isOneTime() {
        return isOneTime;
    }

    public void setOneTime(boolean oneTime) {
        isOneTime = oneTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            System.out.println("Id is null");
        }
        this.id = id;
    }
}
