package msa.customer.exception;

public class StoreNullException extends RuntimeException {

    private static final String NONEXISTENT_STORE_MESSAGE = "This store doesn't exist. (storeId = %s)";

    public StoreNullException(String storeId) {
        super(String.format(NONEXISTENT_STORE_MESSAGE, storeId));
    }

}