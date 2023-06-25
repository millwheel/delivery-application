package msa.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.entity.Manager;
import msa.restaurant.dto.manager.ManagerRequestDto;
import msa.restaurant.entity.Store;
import msa.restaurant.entity.StorePartInfo;
import msa.restaurant.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Manager> getManager(String managerId){
        return memberRepository.findById(managerId);
    }

    public void updateManager(String managerId, ManagerRequestDto data){
        memberRepository.update(managerId, data);
    }

}
