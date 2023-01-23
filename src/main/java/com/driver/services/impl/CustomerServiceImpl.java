package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.CabRepository;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Autowired
	CabRepository cabRepository;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer = customerRepository2.getOne(customerId);
		customerRepository2.delete(customer);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList = driverRepository2.findAllByOrderByIdAsc();
		Driver driver1 = null;
		for(Driver driver:driverList){
			if(driver.getCab().getAvailable()){
				driver1 = driver;
				break;
			}
		}

		if(driver1==null){
			throw new Exception("No cab available!");
		}
		TripBooking tripBooking = new TripBooking(fromLocation,toLocation,distanceInKm);
		Customer customer = customerRepository2.getOne(customerId);
		tripBooking.setCustomer(customer);
		tripBooking.setDriver(driver1);
		tripBooking.setStatus(TripStatus.CONFIRMED);
		int bill = distanceInKm*driver1.getCab().getPerKmRate();
		tripBooking.setBill(bill);

		List<TripBooking> listdriver = driver1.getTripBookingList();
		if(listdriver==null)
			listdriver = new ArrayList<>();
		listdriver.add(tripBooking);
		driver1.setTripBookingList(listdriver);


		List<TripBooking> customerList = customer.getTripBookingList();
		if(customerList==null)
			customerList = new ArrayList<>();
		customerList.add(tripBooking);
		customer.setTripBookingList(customerList);


		customerRepository2.save(customer);
		driverRepository2.save(driver1);
		tripBookingRepository2.save(tripBooking);

  		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.getOne(tripId);
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.getDriver().getCab().setAvailable(true);
		tripBooking.setBill(0);
		tripBookingRepository2.save(tripBooking);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.getOne(tripId);
		tripBooking.setStatus(TripStatus.COMPLETED);
		tripBooking.getDriver().getCab().setAvailable(true);
		tripBookingRepository2.save(tripBooking);
	}
}
