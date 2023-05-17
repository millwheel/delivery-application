package msa.customer.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Getter
@Setter
public class MemberForm {

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private GeoJsonPoint location;

}
