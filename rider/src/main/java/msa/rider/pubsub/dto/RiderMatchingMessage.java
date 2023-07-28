package msa.rider.pubsub.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RiderMatchingMessage {
    private String riderId;
    private String orderId;

    public RiderMatchingMessage(String riderId, String orderId) {
        this.riderId = riderId;
        this.orderId = orderId;
    }
}
