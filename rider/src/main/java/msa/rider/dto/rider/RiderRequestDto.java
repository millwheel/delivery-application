package msa.rider.dto.rider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

@Getter
@Setter
public class RiderRequestDto {
    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
}
