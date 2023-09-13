package msa.customer.exception;

public class MenuLargeException extends IllegalArgumentException{

    public MenuLargeException() {
        super("Menu count is too large.");
    }
}
