package msa.restaurant.DTO;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.DAO.Store;

import java.util.List;

@Setter
@Getter
public class ManagerForm {

    private String name;
    private String email;
    private String phoneNumber;
    private List<Store> storeList;

}
