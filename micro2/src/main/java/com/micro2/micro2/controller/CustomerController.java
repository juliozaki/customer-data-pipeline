package com.micro2.micro2.controller;

import com.micro1.micro1.dto.CustomerDto;
import com.micro2.micro2.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/customers/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/mongo")
    public ResponseEntity<CustomerDto> getAll() {
        CustomerDto customerDto = new CustomerDto();
        try {
            customerDto = customerService.getCustomers();
            customerDto.setCode(HttpStatus.OK.value());
            customerDto.setMessage("Successfully");
            return new ResponseEntity<CustomerDto>(customerDto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            customerDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            customerDto.setMessage(e.getMessage());
            return new ResponseEntity<CustomerDto>(customerDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
