package msa.restaurant.exception;

public class MenuNonexistentException extends NullPointerException{

    private static final String NONEXISTENT_MENU_MESSAGE = "This menu doesn't exist. (menuId = %s)";

    public MenuNonexistentException(String menuId) {
        super(String.format(NONEXISTENT_MENU_MESSAGE, menuId));
    }
}
