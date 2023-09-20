package msa.restaurant.exception;

public class MenuMismatchException extends RuntimeException {
    private static final String MENU_MISMATCH_MESSAGE = "This menu (menuId = %s) doesn't belong to the store (storeId = %s)";
    public MenuMismatchException(String menuId, String storeId) {
        super(String.format(MENU_MISMATCH_MESSAGE, menuId, storeId));
    }
}
