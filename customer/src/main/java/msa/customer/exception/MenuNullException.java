package msa.customer.exception;

public class MenuNullException extends NullPointerException{

    private static final String NONEXISTENT_MENU_MESSAGE = "This menu doesn't exist. (menuId = %s)";

    public MenuNullException(String menuId) {
        super(String.format(NONEXISTENT_MENU_MESSAGE, menuId));
    }
}
