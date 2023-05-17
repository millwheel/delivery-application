package msa.customer.service;

import msa.customer.DAO.Location;
import msa.customer.DAO.Member;
import msa.customer.DTO.MemberForm;
import msa.customer.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberService memberService;
    private JoinService joinService;
    private final String ID = "1315-0045-7784-0159";
    private final String NAME = "John";
    private final String EMAIL = "john@onemail.co.kr";
    private final String PHONE_NUMBER = "01023459988";
    private final String ADDRESS = "서울시 강남구 테헤란로 131";
    private final String ADDRESS_DETAIL = "first floor";
    private final Location LOCATION = new Location(127.032937953168, 37.5000818732753);


    @Autowired
    MemberServiceTest(MemberRepository memberRepository, MemberService memberService, JoinService joinService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.joinService = joinService;
    }

    @DisplayName("회원 정보 조회")
    @Test
    void getMemberInfoTest(){
        // given
        MemberForm memberForm = new MemberForm();
        memberForm.setName(NAME);
        memberForm.setEmail(EMAIL);
        memberForm.setPhoneNumber(PHONE_NUMBER);
        memberForm.setAddress(ADDRESS);
        memberForm.setAddressDetail(ADDRESS_DETAIL);
        // when
        memberService.updateUserInfo(ID, memberForm);
        MemberForm userInfo = memberService.getUserInfo(ID);
        // then
        assertThat(userInfo.getName()).isEqualTo(memberForm.getName());
        assertThat(userInfo.getEmail()).isEqualTo(memberForm.getEmail());
        assertThat(userInfo.getPhoneNumber()).isEqualTo(memberForm.getPhoneNumber());
        assertThat(userInfo.getAddress()).isEqualTo(memberForm.getAddress());
        assertThat(userInfo.getAddressDetail()).isEqualTo(memberForm.getAddressDetail());
    }

    @DisplayName("회원 정보에 주소 입력 시 좌표 자동 입력 테스트")
    @Test
    void setAddressTest() {
        // given
        joinService.joinMember(ID, EMAIL);
        // when
        memberService.setAddress(ID, ADDRESS);
        MemberForm userInfo = memberService.getUserInfo(ID);
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
        MemberForm data = new MemberForm();
        data.setName("Jonny");
        data.setAddress("서울특별시 강북구 도봉로 232");
        // when
        memberService.updateUserInfo(ID, data);
        MemberForm userInfo = memberService.getUserInfo(ID);
        // then
        assertThat(userInfo.getName()).isEqualTo("Jonny");
        assertThat(userInfo.getAddress()).isEqualTo("서울특별시 강북구 도봉로 232");
    }
}