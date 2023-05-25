package msa.customer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcmDto {

    @NotEmpty(message = "ecmId값이 빈값입니다.")
    private String ecmId;
}
