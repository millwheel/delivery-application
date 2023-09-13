package msa.customer.exception;

public class MenuEmptyException extends RuntimeException{

    private static final String EMPTY_MENU_MESSAGE = "This menu doesn't exist. (menuId = %s)";

    public MenuEmptyException(String menuId) {
        super(String.format(EMPTY_MENU_MESSAGE, menuId));
    }
}
