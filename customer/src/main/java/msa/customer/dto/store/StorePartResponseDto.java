package msa.customer.dto.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.customer.entity.store.Store;

@Setter
@Getter
@NoArgsConstructor
public class StorePartResponseDto {
    private String storeId;
    private String name;
    private String introduction;
    private boolean open;

    public StorePartResponseDto(Store store) {
        storeId = store.getStoreId();
        name = store.getName();
        introduction = store.getIntroduction();
        open = store.getOpen();
    }
}
