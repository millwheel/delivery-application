package msa.restaurant.entity.order;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {

    ORDER_REQUEST, ORDER_ACCEPT, ORDER_DENY, RIDER_ASSIGNED, FOOD_READY, DELIVERY_IN_PROGRESS, DELIVERY_COMPLETE, ORDER_CANCELED

}
