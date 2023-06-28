package msa.rider.dto.rider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.rider.entity.member.Rider;

@Getter
@Setter
@NoArgsConstructor
public class RiderPartDto {
    private String riderId;
    private String name;
    private String phoneNumber;

    public RiderPartDto(Rider rider){
        riderId = rider.getRiderId();
        name = rider.getName();
        phoneNumber = rider.getPhoneNumber();
    }
}
