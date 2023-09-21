package msa.customer.exception;

public class OrderNonexistentException extends NullPointerException{

    private static final String NONEXISTENT_ORDER_MESSAGE = "This order doesn't exist. (orderId = %s)";

    public OrderNonexistentException(String orderId) {
        super(String.format(NONEXISTENT_ORDER_MESSAGE, orderId));
    }
}
