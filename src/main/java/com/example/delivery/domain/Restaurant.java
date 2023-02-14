package com.example.delivery.domain;

import java.util.List;

public class Restaurant {
    Long id;
    String name;
    String address;
    String region;
    List<Long> menuId;
    Long ownerId;
    String phoneNumber;
}
