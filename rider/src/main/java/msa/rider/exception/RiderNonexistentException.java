package msa.rider.exception;

public class RiderNonexistentException extends NullPointerException{

    private static final String NONEXISTENT_BASKET_MESSAGE = "The rider doesn't exist. (riderId = %s)";

    public RiderNonexistentException(String riderId) {
        super(String.format(NONEXISTENT_BASKET_MESSAGE, riderId));
    }
}
