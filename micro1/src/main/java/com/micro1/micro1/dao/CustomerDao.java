package com.micro1.micro1.dao;

import com.micro1.micro1.model.Address;
import com.micro1.micro1.model.Customer;
import com.micro1.micro1.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

@Service
public class CustomerDao {

    private final static String FIND_ALL = "SELECT c.C_CUSTOMER_ID, c.C_FIRST_NAME, " +
            "c.C_LAST_NAME, c.C_BIRTH_DAY, c.C_BIRTH_MONTH, " +
            "c.C_BIRTH_YEAR, c.C_EMAIL_ADDRESS, a.CA_STREET_NAME, " +
            "a.CA_STREET_NUMBER, a.CA_CITY, a.CA_STATE, a.CA_COUNTRY " +
            "FROM CUSTOMER c INNER JOIN " +
            "CUSTOMER_ADDRESS a ON a.ca_address_id = C.C_CUSTOMER_ID " +
            "LIMIT ? OFFSET ?";
    private final static String FIND_BY_ID = "SELECT c.C_CUSTOMER_ID, c.C_FIRST_NAME, " +
            "c.C_LAST_NAME, c.C_BIRTH_DAY, c.C_BIRTH_MONTH, " +
            "c.C_BIRTH_YEAR, c.C_EMAIL_ADDRESS, a.CA_STREET_NAME, " +
            "a.CA_STREET_NUMBER, a.CA_CITY, a.CA_STATE, a.CA_COUNTRY " +
            "FROM CUSTOMER c INNER JOIN " +
            "CUSTOMER_ADDRESS a ON a.ca_address_id = C.C_CUSTOMER_ID " +
            "WHERE c.C_CUSTOMER_ID = ?";

    @Value("${user.db.snowflake.connection}")
    private String url;

    private Connection getConnection () {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }

        return  connection;
    }

    public Customer[] getAll(int rows, int page) {
        Customer [] customers = new Customer[rows];
        Connection connection;
        Utils utils = new Utils();
        try {
            connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            preparedStatement.setInt(1, rows);
            preparedStatement.setInt(2, (rows * (page - 1)));
            ResultSet resultSet = preparedStatement.executeQuery();
            int i=0;
            while (resultSet.next()) {
                customers[i] = utils.setCustomerFromResultSet(resultSet);
                i++;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return customers;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return new Customer [1];
        }
    }

    public Customer getById(String customerId) {
        Customer customers = new Customer();
        Connection connection;
        Utils utils = new Utils();
        try {
            connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setString(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customers = utils.setCustomerFromResultSet(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return customers;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return customers;
        }
    }
}
