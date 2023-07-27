package msa.restaurant.pubsub.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ManagerMatchingMessage {
    private String customerId;
    private String orderId;

    public ManagerMatchingMessage(String customerId, String orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }
}
