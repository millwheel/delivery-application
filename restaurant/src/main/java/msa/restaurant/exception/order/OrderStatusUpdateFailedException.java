package msa.restaurant.exception.order;

import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.exception.DeliveryRestaurantException;
import msa.restaurant.service.order.OrderStatusUpdatePolicy;

public class OrderStatusUpdateFailedException extends DeliveryRestaurantException {

    private static final String UPDATE_FAILED_MESSAGE = "Updating order status to %s failed. (OrderStatusUpdatePolicy = %s)";

    public OrderStatusUpdateFailedException(OrderStatus status, OrderStatusUpdatePolicy policy) {
        super(String.format(UPDATE_FAILED_MESSAGE, status, policy));
    }

}
