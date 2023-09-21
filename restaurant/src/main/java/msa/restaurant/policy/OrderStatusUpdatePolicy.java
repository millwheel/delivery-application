package msa.restaurant.policy;

import lombok.RequiredArgsConstructor;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.exception.OrderStatusUnchangeableException;

import java.util.Set;

@RequiredArgsConstructor
public enum OrderStatusUpdatePolicy {

    ACCEPT_ORDER_POLICY(Set.of(OrderStatus.ORDER_REQUEST)),
    FOOD_READY_POLICY(Set.of(OrderStatus.RIDER_ASSIGNED));

    private final Set<OrderStatus> possibleStatuses;

    public void checkStatusUpdatable(OrderStatus nowStatus) {
        final var isUpdatable = this.possibleStatuses.contains(nowStatus);

        if (!isUpdatable) {
            throw new OrderStatusUnchangeableException(nowStatus);
        }
    }

}
