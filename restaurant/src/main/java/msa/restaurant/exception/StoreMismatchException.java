package msa.restaurant.exception;

public class StoreMismatchException extends RuntimeException{

    private static final String STORE_MISMATCH_MESSAGE = "This store (storeId = %s) doesn't belong to the manager (managerId = %s)";
    public StoreMismatchException(String storeId, String managerId) {
        super(String.format(STORE_MISMATCH_MESSAGE, storeId, managerId));
    }
}
