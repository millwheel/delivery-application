package msa.customer.exception;

public class FoodKindNonexistentException extends NullPointerException{

    private static final String WRONG_FOODKIND_MESSAGE = "Wrong food Kind. %s is not in the list.";

    public FoodKindNonexistentException(String foodKind) {
        super(String.format(WRONG_FOODKIND_MESSAGE, foodKind));
    }
}
