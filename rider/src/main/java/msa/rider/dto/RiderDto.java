package msa.rider.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

@Getter
@Setter
public class RiderDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;
}
