package msa.rider.policy;

import lombok.RequiredArgsConstructor;
import msa.rider.entity.order.OrderStatus;
import msa.rider.exception.OrderStatusUnchangeableException;

import java.util.Set;

@RequiredArgsConstructor
public enum OrderRiderAssignPolicy {
    ACCEPT_ORDER_POLICY(Set.of(OrderStatus.ORDER_ACCEPT));

    private final Set<OrderStatus> possibleStatuses;

    public void checkStatusAssignable(OrderStatus nowStatus) {
        final var isAssignable = this.possibleStatuses.contains(nowStatus);

        if (!isAssignable) {
            throw new OrderStatusUnchangeableException(nowStatus);
        }
    }
}
