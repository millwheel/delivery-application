package msa.customer.exception.store;

import msa.customer.entity.store.Store;
import msa.customer.exception.DeliveryCustomerException;

public class StoreEmptyException extends DeliveryCustomerException {

    private static final String EMPTY_STORE_MESSAGE = "Store is empty. (storeId = %s)";

    public StoreEmptyException(Store store) {
        super(String.format(EMPTY_STORE_MESSAGE, store.getStoreId()));
    }

    public StoreEmptyException(String storeId) {
        super(String.format(EMPTY_STORE_MESSAGE, storeId));
    }

}