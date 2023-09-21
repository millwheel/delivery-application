package msa.customer.exception;

public class BasketNonexistentException extends NullPointerException{

    private static final String NONEXISTENT_BASKET_MESSAGE = "This basket doesn't exist. (basketId = %s)";

    public BasketNonexistentException(String basketId) {
        super(String.format(NONEXISTENT_BASKET_MESSAGE, basketId));
    }
}
