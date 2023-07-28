package msa.restaurant.pubsub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StoreOrderMatchingMessage {

    private String storeId;
    private String orderId;

    public StoreOrderMatchingMessage(String storeId, String orderId) {
        this.storeId = storeId;
        this.orderId = orderId;
    }
}
