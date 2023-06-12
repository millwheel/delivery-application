package msa.restaurant.dto;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.Store;

import java.util.List;

@Setter
@Getter
public class ManagerDto {

    private String name;
    private String email;
    private String phoneNumber;
    private List<Store> storeList;

}
