package com.example.delivery.repository.customer;

import com.example.delivery.domain.Customer;

import java.util.Optional;

public interface CustomerRepository {
    void make();
    Optional<Customer> findById(Long id);
    Optional<Customer> findByUserId(String userId);
}
