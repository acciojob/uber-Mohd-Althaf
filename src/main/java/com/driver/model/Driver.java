package com.driver.model;


import java.util.*;

import javax.persistence.*;

@Entity
@Table
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int driverId;

    private String mobile;

    private String password;

    public Driver(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public int getDriverId() {
        return driverId;
    }

    public Driver() {
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

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne
    @JoinColumn
    private Cab cab;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    private List<TripBooking> tripBookingList;

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }
}