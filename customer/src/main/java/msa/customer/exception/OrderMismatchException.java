package msa.customer.exception;

public class OrderMismatchException extends RuntimeException{
    private static final String MISMATCH_ORDER_MESSAGE = "This order (orderId = %s) doesn't belong to the customer (customerId = %s) ";

    public OrderMismatchException(String orderId, String customerId) {
        super(String.format(MISMATCH_ORDER_MESSAGE, orderId, customerId));
    }
}
