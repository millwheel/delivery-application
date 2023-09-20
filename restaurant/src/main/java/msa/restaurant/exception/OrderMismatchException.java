package msa.restaurant.exception;

public class OrderMismatchException extends RuntimeException {

    private static final String ORDER_MISMATCH_MESSAGE = "This order (orderId = %s) doesn't belong to the store (storeId = %s)";
    public OrderMismatchException(String orderId, String storeId) {
        super(String.format(ORDER_MISMATCH_MESSAGE, orderId, storeId));
    }
}