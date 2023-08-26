package msa.restaurant.exception.store;

import msa.restaurant.entity.store.Store;
import msa.restaurant.exception.DeliveryRestaurantException;

public class StoreEmptyException extends DeliveryRestaurantException {

    private static final String EMPTY_STORE_MESSAGE = "Store is empty. (storeId = %s)";

    public StoreEmptyException(Store store) {
        super(String.format(EMPTY_STORE_MESSAGE, store.getStoreId()));
    }

    public StoreEmptyException(String storeId) {
        super(String.format(EMPTY_STORE_MESSAGE, storeId));
    }

}
