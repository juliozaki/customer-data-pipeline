package com.micro1.micro1.service;

import com.micro1.micro1.dao.CustomerDao;
import com.micro1.micro1.dto.CustomerDto;
import com.micro1.micro1.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

@Service
public class CustomerService {

    KafkaTemplate<String, Customer> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String topicKafka;

    @Autowired
    private CustomerDao customerDao;

    public CustomerService(KafkaTemplate<String, Customer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CustomerDto getAll(int rows, int page) {
        try {
            Customer[] customer = customerDao.getAll(rows, page);
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerData(customer);
            customerDto.setCurrentPage(page);
            customerDto.setRowsXPage(rows);
            return customerDto;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new CustomerDto();
        }
    }

    public CustomerDto getById(String customerId) {
        try {
            Customer[] customer = new Customer[1];
            customer[0] = customerDao.getById(customerId);
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerData(customer);
            createCustomerKafka(customer[0]);
            customerDto.setCurrentPage(0);
            customerDto.setRowsXPage(1);
            return customerDto;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new CustomerDto();
        }
    }

    public void createCustomerKafka(Customer customerModel) throws Exception {
        SendResult<String, Customer> result =
                kafkaTemplate.send(topicKafka,customerModel.getCustomerId(), customerModel).get();
        System.out.println("partition() " + result.getRecordMetadata().partition());
        System.out.println("topic() " + result.getRecordMetadata().topic());
        System.out.println("offset() " + result.getRecordMetadata().offset());
    }

}
