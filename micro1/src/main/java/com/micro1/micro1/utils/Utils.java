package com.micro1.micro1.utils;

import com.micro1.micro1.model.Address;
import com.micro1.micro1.model.Customer;

import java.sql.ResultSet;

public class Utils {

    public Customer setCustomerFromResultSet (ResultSet resultSet) {
        try {
            Customer customers = new Customer();
            customers.setCustomerId(resultSet.getString("C_CUSTOMER_ID"));
            customers.setFirstName(resultSet.getString("C_FIRST_NAME"));
            customers.setLastName(resultSet.getString("C_LAST_NAME"));
            customers.setBirthDay(resultSet.getInt("C_BIRTH_DAY"));
            customers.setBirthMonth(resultSet.getInt("C_BIRTH_MONTH"));
            customers.setBirthYear(resultSet.getInt("C_BIRTH_YEAR"));
            customers.setEmail(resultSet.getString("C_EMAIL_ADDRESS"));
            Address address = new Address();
            address.setStreet(resultSet.getString("CA_STREET_NAME"));
            address.setNumber(resultSet.getString("CA_STREET_NUMBER"));
            address.setCity(resultSet.getString("CA_CITY"));
            address.setState(resultSet.getString("CA_STATE"));
            address.setCountry(resultSet.getString("CA_COUNTRY"));
            customers.setAddress(address);

            return customers;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new Customer();
        }
    }
}
