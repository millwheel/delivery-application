package msa.customer.pubsub.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerMatchingMessage {
    private String customerId;
    private String orderId;

    public CustomerMatchingMessage(String customerId, String orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }
}
