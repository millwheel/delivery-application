package msa.customer.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResult {
    private String code;
    private String message;

    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
