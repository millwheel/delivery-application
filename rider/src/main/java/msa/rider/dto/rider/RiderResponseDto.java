package msa.rider.dto.rider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.rider.entity.member.Rider;
import org.springframework.data.geo.Point;

@Getter
@Setter
@NoArgsConstructor
public class RiderResponseDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Point location;

    public RiderResponseDto(Rider rider) {
        name = rider.getName();
        email = rider.getEmail();
        phoneNumber = rider.getPhoneNumber();
        address = rider.getAddress();
        location = rider.getLocation();
    }
}
