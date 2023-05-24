package msa.customer.repository.member;

import msa.customer.DAO.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class MemberRepositoryTest {

//    @Mock
    private MemberRepository memberRepository;

    @Autowired
    MemberRepositoryTest(MemberRepository memberRepository) {
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
        Member member = new Member();
        member.setMemberId("1350");
        member.setName("John");
        member.setEmail("john@onemail.co.kr");
        member.setAddress("Manhattan central park");
        member.setAddressDetail("first floor");
        BDDMockito.given(memberRepository.findById(id)).willReturn(Optional.of(member));
        // when
//        String id = memberRepository.make(member);
        Member savedMember = memberRepository.findById(id).get();
        // then
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getName()).isEqualTo("John");
        assertThat(savedMember.getEmail()).isEqualTo("john@onemail.co.kr");
        assertThat(savedMember.getAddress()).isEqualTo("Manhattan central park");
        assertThat(savedMember.getAddressDetail()).isEqualTo("first floor");
    }

}