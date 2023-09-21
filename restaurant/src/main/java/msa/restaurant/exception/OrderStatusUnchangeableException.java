package msa.restaurant.exception;

import msa.restaurant.entity.order.OrderStatus;

public class OrderStatusUnchangeableException extends RuntimeException{

    private static final String NONEXISTENT_ORDER_MESSAGE = "The current order status (order status = %s) is not changeable.";

    public OrderStatusUnchangeableException(OrderStatus orderStatus) {
        super(String.format(NONEXISTENT_ORDER_MESSAGE, orderStatus.toString()));
    }
}
