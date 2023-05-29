package msa.restaurant.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

@Setter
@Getter
public class MemberForm {

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;

}
