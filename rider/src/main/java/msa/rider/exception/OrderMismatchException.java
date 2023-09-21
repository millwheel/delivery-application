package msa.rider.exception;

public class OrderMismatchException extends RuntimeException{
    private static final String MISMATCH_ORDER_MESSAGE = "This order (orderId = %s) doesn't belong to the rider (riderId = %s) ";

    public OrderMismatchException(String orderId, String riderId) {
        super(String.format(MISMATCH_ORDER_MESSAGE, orderId, riderId));
    }
}
