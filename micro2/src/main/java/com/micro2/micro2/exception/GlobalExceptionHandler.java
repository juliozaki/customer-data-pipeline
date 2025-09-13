package com.micro2.micro2.exception;

import com.micro1.micro1.dto.CustomerDto;
import com.micro1.micro1.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomerDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerData(new Customer[1]);
        customerDto.setCode(HttpStatus.BAD_REQUEST.value());
        customerDto.setMessage("Error: " + ex.getMessage());
        return new ResponseEntity<CustomerDto>(customerDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomerDto> handleGeneralException(Exception ex) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerData(new Customer[1]);
        customerDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        customerDto.setMessage("Error: " + ex.getMessage());
        return new ResponseEntity<CustomerDto>(customerDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
