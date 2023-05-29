package msa.customer.repository.member;

import msa.customer.DAO.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {

//    @Mock
    private MemberRepository memberRepository;

    @Autowired
    CustomerRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @AfterEach
    void deleteAllTestData(){
        memberRepository.deleteAll();
    }

    @DisplayName("회원 저장 후 조회")
    @Test
    void saveMemberAndReadTest(){
        // given
        String id = "1234";
        Customer customer = new Customer();
        customer.setMemberId("1350");
        customer.setName("John");
        customer.setEmail("john@onemail.co.kr");
        customer.setAddress("Manhattan central park");
        customer.setAddressDetail("first floor");
        BDDMockito.given(memberRepository.findById(id)).willReturn(Optional.of(customer));
        // when
//        String id = memberRepository.make(customer);
        Customer savedCustomer = memberRepository.findById(id).get();
        // then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("John");
        assertThat(savedCustomer.getEmail()).isEqualTo("john@onemail.co.kr");
        assertThat(savedCustomer.getAddress()).isEqualTo("Manhattan central park");
        assertThat(savedCustomer.getAddressDetail()).isEqualTo("first floor");
    }

}