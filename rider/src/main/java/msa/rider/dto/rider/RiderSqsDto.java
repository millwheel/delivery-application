package msa.rider.dto.rider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.rider.entity.member.Rider;
import org.springframework.data.geo.Point;

@Getter
@Setter
@NoArgsConstructor
public class RiderSqsDto {
    private String riderId;
    private String name;
    private String phoneNumber;
    private Point riderLocation;

    public RiderSqsDto(Rider rider){
        riderId = rider.getRiderId();
        name = rider.getName();
        phoneNumber = rider.getPhoneNumber();
        riderLocation = rider.getLocation();
    }
}
