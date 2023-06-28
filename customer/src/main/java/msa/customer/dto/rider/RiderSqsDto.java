package msa.customer.dto.rider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RiderSqsDto {
    private String riderId;
    private String name;
    private String phoneNumber;
}
