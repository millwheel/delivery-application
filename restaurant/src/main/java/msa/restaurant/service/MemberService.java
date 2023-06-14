package msa.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.entity.Manager;
import msa.restaurant.entity.Store;
import msa.restaurant.dto.manager.ManagerDto;
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

    public Optional<List<Store>> getStoreList(String managerId){
        return memberRepository.findById(managerId).map(Manager::getStoreList);
    }

    public ManagerDto getUserInfo(String managerId){
        ManagerDto managerDto = new ManagerDto();
        memberRepository.findById(managerId).ifPresent(manager -> {
            managerDto.setName(manager.getName());
            managerDto.setPhoneNumber(manager.getPhoneNumber());
        });
        return managerDto;
    }

    public void updateUserInfo(String managerId, ManagerDto data){
        memberRepository.update(managerId, data);
    }

    public void updateStoreList(String managerId, Store store){
        List<Store> storeList = getStoreList(managerId).orElseGet(ArrayList::new);
        storeList.add(store);
        memberRepository.updateStoreList(managerId, storeList);
    }

    public void deleteStoreFromList(String managerId, String storeId){
        memberRepository.deleteStoreFromList(managerId, storeId);
    }

}
