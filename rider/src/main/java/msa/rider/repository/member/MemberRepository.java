package msa.rider.repository.member;

import msa.rider.dto.rider.RiderAddressRequestDto;
import msa.rider.dto.rider.RiderResponseDto;
import msa.rider.entity.member.Rider;
import org.springframework.data.geo.Point;

import java.util.Optional;

public interface MemberRepository {
    String create(Rider rider);
    Optional<Rider> findById(String riderId);
    void update(String riderId, RiderResponseDto data);
    void update(String riderId, RiderResponseDto data, Point location);
    void updateAddress(String riderId, RiderAddressRequestDto data, Point location);
    void deleteById(String riderId);
}
