package msa.customer.dto;

import lombok.Getter;
import lombok.Setter;
import msa.customer.entity.FoodKindType;
import msa.customer.entity.Menu;
import org.springframework.data.geo.Point;

import java.util.List;

@Getter
@Setter
public class StoreSqsDto {
    private String storeId;
    private String name;
    private FoodKindType foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private double longitude;
    private double latitude;
    private String introduction;
}
