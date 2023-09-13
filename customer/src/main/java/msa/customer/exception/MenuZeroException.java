package msa.customer.exception;

public class MenuZeroException extends IllegalCallerException{

    public MenuZeroException(){
        super("Menu count should not be 0.");
    }
}
