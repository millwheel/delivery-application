package msa.restaurant.exception;

public class DeliveryRestaurantException extends RuntimeException {

    public DeliveryRestaurantException(String message) {
        super(message);
    }

    public DeliveryRestaurantException(String message, Throwable cause) {
        super(message, cause);
    }

}
