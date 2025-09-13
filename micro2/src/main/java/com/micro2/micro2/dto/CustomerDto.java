package com.micro2.micro2.dto;

import com.micro1.micro1.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private int code;
    private String message;
    private Customer[] customerData;
    private int rowsXPage;
    private int currentPage;
}
