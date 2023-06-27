package msa.rider.dto.rider;

import lombok.Getter;
import lombok.Setter;
import msa.rider.entity.member.Rider;
import org.springframework.data.geo.Point;

@Getter
@Setter
public class RiderResponseDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;

    public RiderResponseDto(Rider rider) {
        name = rider.getName();
        email = rider.getEmail();
        phoneNumber = rider.getPhoneNumber();
        address = rider.getAddress();
        addressDetail = rider.getAddressDetail();
        location = rider.getLocation();
    }
}
