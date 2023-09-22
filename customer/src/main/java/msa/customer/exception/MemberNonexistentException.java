package msa.customer.exception;

public class MemberNonexistentException extends IllegalArgumentException{

    private static final String NONEXISTENT_MEMBER_MESSAGE = "This customer doesn't exist.(customerId = %s)";

    public MemberNonexistentException(String memberId) {
        super(String.format(NONEXISTENT_MEMBER_MESSAGE, memberId));
    }
}
