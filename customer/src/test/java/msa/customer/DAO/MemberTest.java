package msa.customer.DAO;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    void createMemberTest(){
        Member member = new Member();
        String id = "test-id-1393-1787";
        String email = "testemail@outlook.kr";
        String name = "John";
        String address = "atlantis city";
        String addressDetail = "in front of door, 1st floor";

        member.setMemberId(id);
        member.setEmail(email);
        member.setName(name);
        member.setAddress(address);
        member.setAddressDetail(addressDetail);

        assertThat(member.getMemberId()).isEqualTo(id);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getAddress()).isEqualTo(address);
        assertThat(member.getAddressDetail()).isEqualTo(addressDetail);
    }

}