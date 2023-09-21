package msa.rider.repository.member;

import msa.rider.dto.rider.RiderAddressRequestDto;
import msa.rider.dto.rider.RiderRequestDto;
import msa.rider.dto.rider.RiderResponseDto;
import msa.rider.entity.member.Rider;
import org.springframework.data.geo.Point;

import java.util.Optional;

public interface MemberRepository {
    String create(Rider rider);
    Rider readRider(String riderId);
    Rider update(String riderId, RiderRequestDto data);
    void update(String riderId, RiderRequestDto data, Point location);
    void updateAddress(String riderId, String address, Point location);
}
