package com.micro2.micro2.service;

import com.micro1.micro1.dto.CustomerDto;
import com.micro1.micro1.model.Customer;
import com.micro2.micro2.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics="customer-topic")
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @KafkaHandler
    public void handle(Customer customer) {
        try {
            customerDao.setCustomer(customer);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public CustomerDto getCustomers () {
        try {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerData(customerDao.getCustomers());
            customerDto.setCurrentPage(1);
            customerDto.setRowsXPage(customerDto.getCustomerData().length);
            return customerDto;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return new CustomerDto();
        }
    }
}
