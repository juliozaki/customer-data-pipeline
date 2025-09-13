package com.micro2.micro2.dao;

import com.micro1.micro1.model.Customer;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.LocalDateTime;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
public class CustomerDao {

    @Value("${user.db.mongodb.connection}")
    private String url;

    @Value("${user.db.mongodb.dataBase}")
    private String dataBaseName;

    public void setCustomer (Customer customer) {
        MongoDatabase connection = null;
        String connectionString = url;
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(dataBaseName).withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Customer> collection = database.getCollection(dataBaseName, Customer.class);
            Document query = new Document("customerId", customer.getCustomerId());
            FindIterable<Customer> findCustomer = collection.find(query);
            boolean exist = false;
            for (Customer doc : findCustomer) {
                exist = true;
            }
            if(!exist) {
                customer.setReceivedAt(LocalDateTime.now());
                collection.insertOne(customer);
                System.out.println("New customer");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Customer[] getCustomers () {
        MongoDatabase connection = null;
        String connectionString = url;
        Customer[] customers;
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(dataBaseName).withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Customer> collection = database.getCollection(dataBaseName, Customer.class);
            customers = new Customer[(int) collection.countDocuments()];
            int i=0;
            for (Customer customer : collection.find()) {
                customers[i] = customer;
                i++;
            }

            return customers;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new Customer[1];
        }
    }
}
