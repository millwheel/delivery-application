package msa.restaurant.service.order;

import lombok.RequiredArgsConstructor;
import msa.restaurant.entity.order.OrderStatus;

import java.util.Set;

@RequiredArgsConstructor
public enum OrderStatusUpdatePolicy {

    ACCEPT_ORDER_POLICY(Set.of(OrderStatus.ORDER_REQUEST)),
    FOOD_READY_POLICY(Set.of(OrderStatus.RIDER_ASSIGNED));

    private final Set<OrderStatus> possibleStatuses;

    public OrderStatus checkStatusUpdatable(OrderStatus nowStatus) {
        final var isUpdatable = this.possibleStatuses.contains(nowStatus);

        if (!isUpdatable) {
            throw new RuntimeException();
        }
        if (nowStatus == OrderStatus.ORDER_REQUEST){
            return OrderStatus.ORDER_ACCEPT;
        }else if(nowStatus == OrderStatus.RIDER_ASSIGNED){
            return OrderStatus.FOOD_READY;
        }else {
            throw new RuntimeException();
        }
    }

}
