package msa.rider.service.member;

import lombok.extern.slf4j.Slf4j;
import msa.rider.dto.rider.RiderAddressRequestDto;
import msa.rider.dto.rider.RiderRequestDto;
import msa.rider.entity.member.Rider;
import msa.rider.dto.rider.RiderResponseDto;
import msa.rider.repository.member.MemberRepository;
import msa.rider.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final AddressService addressService;
    @Autowired
    public MemberService(MemberRepository memberRepository, AddressService addressService) {
        this.memberRepository = memberRepository;
        this.addressService = addressService;
    }

    public Rider getRider(String riderId){
        return memberRepository.readRider(riderId);
    }

    public void updateRider(String riderId, RiderRequestDto data){
        if (data.getAddress() == null) {
            memberRepository.update(riderId, data);
        } else {
            Point coordinate = addressService.getCoordinate(data.getAddress());
            memberRepository.update(riderId, data, coordinate);
        }
    }

    public void updateAddress(String riderId, String address){
        Point coordinate = addressService.getCoordinate(address);
        memberRepository.updateAddress(riderId, address, coordinate);
    }

}
