package msa.restaurant.exception;

public class StoreNonexistentException extends NullPointerException {

    private static final String NONEXISTENT_STORE_MESSAGE = "This store doesn't exist. (storeId = %s)";

    public StoreNonexistentException(String storeId) {
        super(String.format(NONEXISTENT_STORE_MESSAGE, storeId));
    }

}