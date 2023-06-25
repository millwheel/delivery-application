package msa.customer.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDto {
    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
}
