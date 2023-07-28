package msa.rider.pubsub.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RiderMatchingMessage {
    private String customerId;
    private String orderId;

    public RiderMatchingMessage(String customerId, String orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }
}
