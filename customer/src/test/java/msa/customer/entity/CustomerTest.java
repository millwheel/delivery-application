package msa.customer.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void createMemberTest(){
        Customer customer = new Customer();
        String id = "test-id-1393-1787";
        String email = "john@onemail.co.kr";
        String name = "John";
        String address = "atlantis city";
        String addressDetail = "in front of door, 1st floor";

        customer.setMemberId(id);
        customer.setEmail(email);
        customer.setName(name);
        customer.setAddress(address);
        customer.setAddressDetail(addressDetail);

        assertThat(customer.getMemberId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getAddress()).isEqualTo(address);
        assertThat(customer.getAddressDetail()).isEqualTo(addressDetail);
    }

}