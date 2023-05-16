package msa.customer.DAO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void createRestaurantTest(){
        // given
        Menu oliveChicken = new Menu();
        Menu redChicken = new Menu();
        oliveChicken.setName("Golden olive chicken");
        oliveChicken.setPrice(20000);
        oliveChicken.setDescription("Original ordinary chicken");
        redChicken.setName("Red pepper chicken");
        redChicken.setPrice(21000);
        redChicken.setDescription("Hot spicy chicken with red pepper");
        List<Menu> menuList = new ArrayList<>();
        menuList.add(oliveChicken);
        menuList.add(redChicken);
        // when
        Restaurant restaurant = new Restaurant();
        String name = "Chicken run";
        String introduction = "Most delicious chicken in this town.";
        String address = "Atlantis central park.";
        restaurant.setName(name);
        restaurant.setIntroduction(introduction);
        restaurant.setAddress(address);
        restaurant.setMenuList(menuList);
        // then
        assertThat(restaurant.getName()).isEqualTo(name);
        assertThat(restaurant.getIntroduction()).isEqualTo(introduction);
        assertThat(restaurant.getAddress()).isEqualTo(address);
        assertThat(restaurant.getMenuList()).contains(oliveChicken, redChicken);
    }

}