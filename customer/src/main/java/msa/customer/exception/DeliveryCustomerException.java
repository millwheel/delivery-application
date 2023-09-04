package msa.customer.exception;

public class DeliveryCustomerException extends RuntimeException{

    public DeliveryCustomerException(String message) {
        super(message);
    }

    public DeliveryCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}
