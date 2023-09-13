package msa.customer.exception;

public class StoreMismatchException extends RuntimeException{

    private static final String STORE_MISMATCH_MESSAGE = "store id: %s doesn't match existing store id in basket";

    public StoreMismatchException(String storeId) {
        super(String.format(STORE_MISMATCH_MESSAGE, storeId));
    }
}
