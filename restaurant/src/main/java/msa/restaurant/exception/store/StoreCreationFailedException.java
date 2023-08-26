package msa.restaurant.exception.store;

import msa.restaurant.exception.DeliveryRestaurantException;

public class StoreCreationFailedException extends DeliveryRestaurantException {

    private static final String CREATION_FAILED_MESSAGE = "Store creation failed. (storeId = %s)";

    public StoreCreationFailedException(String storeId) {
        super(String.format(CREATION_FAILED_MESSAGE, storeId));
    }

}