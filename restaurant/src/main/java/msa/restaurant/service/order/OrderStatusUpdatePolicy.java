package msa.restaurant.service.order;

import lombok.RequiredArgsConstructor;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.exception.order.OrderStatusUpdateFailedException;

import java.util.Set;

@RequiredArgsConstructor
public enum OrderStatusUpdatePolicy {

    ACCEPT_ORDER_POLICY(Set.of(OrderStatus.ORDER_ACCEPT)),
    FOOD_READY_POLICY(Set.of(OrderStatus.FOOD_READY));

    private final Set<OrderStatus> possibleStatuses;

    public void checkStatusUpdatable(OrderStatus nextStatus) {
        final var isUpdatable = this.possibleStatuses.contains(nextStatus);

        if (!isUpdatable) {
            throw new OrderStatusUpdateFailedException(nextStatus, this);
        }
    }

}
