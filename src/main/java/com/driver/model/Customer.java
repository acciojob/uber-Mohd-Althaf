package com.driver.model;


import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;

    private String mobile;

    private String password;

    public Customer(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Customer() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<TripBooking> tripBookingList;

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }
}