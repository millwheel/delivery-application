package msa.restaurant.dto.manager;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.member.Manager;

@Getter
@Setter
public class ManagerResponseDto {
    private String name;
    private String email;
    private String phoneNumber;

    public ManagerResponseDto(Manager manager) {
        name = manager.getName();
        email = manager.getEmail();
        phoneNumber = manager.getPhoneNumber();
    }
}
