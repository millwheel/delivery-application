package msa.customer.exception;

public class BasketNullException extends NullPointerException{

    private static final String NONEXISTENT_BASKET_MESSAGE = "This menu doesn't exist. (menuId = %s)";

    public BasketNullException(String menuId) {
        super(String.format(NONEXISTENT_BASKET_MESSAGE, menuId));
    }
}
