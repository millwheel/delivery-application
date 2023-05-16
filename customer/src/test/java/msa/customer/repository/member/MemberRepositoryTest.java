package msa.customer.repository.member;

import msa.customer.DAO.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    private MemberRepository memberRepository;

    @Autowired
    MemberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @DisplayName("회원 저장 후 조회")
    @Test
    void saveMemberAndCheck(){
        // given
        Member member = new Member();
        member.setMemberId("13-50-8523-7084");
        member.setName("John");
        member.setEmail("john@onemail.co.kr");
        member.setAddress("Atlantis central park");
        member.setAddressDetail("first floor");
        // when
        memberRepository.make(member);
        Member member1 = memberRepository.findById("13-50-8523-7084").get();
        // then
        assertThat(member1).isNotNull();
        assertThat(member1.getName()).isEqualTo("John");
        assertThat(member1.getEmail()).isEqualTo("john@onemail.co.kr");
        assertThat(member1.getAddress()).isEqualTo("Atlantis central park");
        assertThat(member1.getAddressDetail()).isEqualTo("first floor");
    }

}