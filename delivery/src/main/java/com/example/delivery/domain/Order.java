package com.example.delivery.domain;

import java.util.List;

public class Order {
    Long id;
    Long resOrderNumber;
    Long customerId;
    Long restaurantId;
    Long riderId;
    List<Long> menuIds;

}
