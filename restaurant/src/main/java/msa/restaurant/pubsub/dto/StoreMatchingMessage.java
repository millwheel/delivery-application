package msa.restaurant.pubsub.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreMatchingMessage {
    private String storeId;
    private String orderId;

    public StoreMatchingMessage(String storeId, String orderId) {
        this.storeId = storeId;
        this.orderId = orderId;
    }
}
