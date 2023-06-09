package msa.restaurant.dto;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.Menu;

import java.util.List;

@Getter
@Setter
public class StoreMenuDto {
    private List<Menu> menuList;
}
