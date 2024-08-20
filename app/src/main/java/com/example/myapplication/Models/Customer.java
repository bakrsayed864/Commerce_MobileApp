package com.example.myapplication.Models;

public class Customer {
    private int id;
    private String name;
    private String username;
    private String password;
    private String gender;
    private String job;
    private String birthDate;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setJob(String job) {
        this.job = job;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getJob() {
        return job;
    }



    public Customer(String name,String username, String password, String gender,String birthDate, String job) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.job=job;
    }
}
