package msa.customer.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
}
