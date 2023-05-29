package msa.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DAO.Manager;
import msa.restaurant.DAO.Restaurant;
import msa.restaurant.DTO.ManagerForm;
import msa.restaurant.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, AddressService addressService) {
        this.memberRepository = memberRepository;
    }

    public Optional<String> getName(String id){
        return memberRepository.findById(id).map(Manager::getName);
    }

    public Optional<String> getEmail(String id){
        return memberRepository.findById(id).map(Manager::getEmail);
    }

    public Optional<String> getPhoneNumber(String id){
        return memberRepository.findById(id).map(Manager::getPhoneNumber);
    }
    public Optional<List<Restaurant>> getRestaurantList(String id){
        return memberRepository.findById(id).map(Manager::getRestaurantList);
    }

    public ManagerForm getUserInfo(String id){
        ManagerForm managerForm = new ManagerForm();
        getName(id).ifPresent(managerForm::setName);
        getEmail(id).ifPresent(managerForm::setEmail);
        getPhoneNumber(id).ifPresent(managerForm::setPhoneNumber);
        return managerForm;
    }

    public void setName(String id, String name){
        memberRepository.setName(id, name);
    }

    public void setPhoneNumber(String id, String phoneNumber){
        memberRepository.setPhoneNumber(id, phoneNumber);
    }

    public void setRestaurantList(String id, List<Restaurant> restaurantList){
        memberRepository.setRestaurantList(id, restaurantList);
    }

    public void updateUserInfo(String id, ManagerForm data){
        String name = data.getName();
        String phoneNumber = data.getPhoneNumber();
        if(name != null) setName(id, name);
        if(phoneNumber != null) setPhoneNumber(id, phoneNumber);
    }

}
