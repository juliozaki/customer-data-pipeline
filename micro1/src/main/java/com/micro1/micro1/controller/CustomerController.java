package com.micro1.micro1.controller;

import com.micro1.micro1.service.CustomerService;
import com.micro1.micro1.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/customers/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerDto> findAll(@RequestParam(name="rows", defaultValue = "10", required = true) int rows,
                                                 @RequestParam(name="page", defaultValue = "1", required = true) int page) {
        CustomerDto customerDto = new CustomerDto();
        try {
            customerDto = customerService.getAll(rows, page);
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

    @GetMapping("/fetch/{customerId}")
    public ResponseEntity<CustomerDto> findById(@PathVariable(name = "customerId", required = true) String customerId) {
        CustomerDto customerDto = new CustomerDto();
        try {
            customerDto = customerService.getById(customerId);
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
