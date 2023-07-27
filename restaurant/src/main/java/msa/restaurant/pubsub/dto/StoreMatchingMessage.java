package msa.restaurant.pubsub.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreMatchingMessage {
    private String storeId;

    public StoreMatchingMessage(String storeId) {
        this.storeId = storeId;
    }
}
