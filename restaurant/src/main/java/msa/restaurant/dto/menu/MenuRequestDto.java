package msa.restaurant.dto.menu;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDto {
    @NotNull(message = "menu name is missing")
    private String name;
    @NotNull(message = "menu price is missing")
    private int price;
    @NotNull(message = "menu description is missing")
    private String description;
}
