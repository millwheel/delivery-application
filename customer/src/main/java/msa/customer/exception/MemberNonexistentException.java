package msa.customer.exception;

public class MemberNonexistentException extends IllegalArgumentException{

    private static final String NONEXISTENT_MEMBER_MESSAGE = "This member doesn't exist. (memberId = %s)";

    public MemberNonexistentException(String memberId) {
        super(String.format(NONEXISTENT_MEMBER_MESSAGE, memberId));
    }
}
