package msa.customer.service;

import msa.customer.entity.Customer;
import msa.customer.dto.CustomerDto;
import msa.customer.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class CustomerServiceTest {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JoinService joinService;
    private final String ID = "1315-0045-7784-0159";
    private final String NAME = "John";
    private final String EMAIL = "john@onemail.co.kr";
    private final String PHONE_NUMBER = "01023459988";
    private final String ADDRESS = "서울시 강남구 테헤란로 131";
    private final String ADDRESS_DETAIL = "first floor";
    private final Point LOCATION = new Point(127.032937953168, 37.5000818732753);

    @Autowired
    CustomerServiceTest(MemberRepository memberRepository, MemberService memberService, JoinService joinService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.joinService = joinService;
    }

    @DisplayName("회원 정보 조회")
    @Test
    void getMemberInfoTest(){
        // given
        Customer customer = new Customer();
        customer.setCustomerId(ID);
        customer.setEmail(EMAIL);
        memberRepository.make(customer);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(NAME);
        customerDto.setPhoneNumber(PHONE_NUMBER);
        customerDto.setAddress(ADDRESS);
        customerDto.setAddressDetail(ADDRESS_DETAIL);
        memberService.updateUserInfo(ID, customerDto);
        // when
        CustomerDto userInfo = memberService.getUserInfo(ID);
        // then
        assertThat(userInfo.getName()).isEqualTo(customerDto.getName());
        assertThat(userInfo.getEmail()).isEqualTo(customer.getEmail());
        assertThat(userInfo.getPhoneNumber()).isEqualTo(customerDto.getPhoneNumber());
        assertThat(userInfo.getAddress()).isEqualTo(customerDto.getAddress());
        assertThat(userInfo.getAddressDetail()).isEqualTo(customerDto.getAddressDetail());
    }

    @DisplayName("회원 정보에 주소 입력 시 좌표 자동 입력 테스트")
    @Test
    void setAddressTest() {
        // given
        joinService.joinMember(ID, EMAIL);
        // when
        memberService.setAddress(ID, ADDRESS);
        CustomerDto userInfo = memberService.getUserInfo(ID);
        // then
        assertThat(userInfo.getAddress()).isEqualTo(ADDRESS);
        assertThat(userInfo.getLocation()).isEqualTo(LOCATION);
    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    void changeUserInfoTest() {
        // given
        joinService.joinMember(ID, EMAIL);
        memberService.setName(ID, NAME);
        memberService.setPhoneNumber(ID, PHONE_NUMBER);
        memberService.setAddress(ID, ADDRESS);
        memberService.setAddressDetail(ID, ADDRESS_DETAIL);
        CustomerDto data = new CustomerDto();
        data.setName("Jonny");
        data.setAddress("서울특별시 강북구 도봉로 232");
        // when
        memberService.updateUserInfo(ID, data);
        CustomerDto userInfo = memberService.getUserInfo(ID);
        // then
        assertThat(userInfo.getName()).isEqualTo("Jonny");
        assertThat(userInfo.getAddress()).isEqualTo("서울특별시 강북구 도봉로 232");
    }
}