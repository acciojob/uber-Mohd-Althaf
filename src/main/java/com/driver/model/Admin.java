package com.driver.model;


import javax.persistence.*;

@Entity
@Table
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int adminId;

    private String username;

    private String password;

    public Admin() {
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getAdminId() {
        return adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setAdminId(int admimId) {
        this.adminId = admimId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}