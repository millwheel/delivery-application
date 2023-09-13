package msa.customer.exception;

public class StoreEmptyException extends RuntimeException {

    private static final String EMPTY_STORE_MESSAGE = "This store doesn't exist. (storeId = %s)";

    public StoreEmptyException(String storeId) {
        super(String.format(EMPTY_STORE_MESSAGE, storeId));
    }

}