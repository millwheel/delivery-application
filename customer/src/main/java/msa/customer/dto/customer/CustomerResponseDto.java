package msa.customer.dto.customer;

import lombok.Getter;
import lombok.Setter;
import msa.customer.entity.Customer;

@Getter
@Setter
public class CustomerResponseDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;

    public CustomerResponseDto(Customer customer) {
        name = customer.getName();
        email = customer.getEmail();
        phoneNumber = customer.getPhoneNumber();
        address = customer.getAddress();
        addressDetail = customer.getAddressDetail();
    }
}
