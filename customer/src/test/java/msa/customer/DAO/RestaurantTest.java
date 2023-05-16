package msa.customer.DAO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void createRestaurantTest(){
        // when
        Restaurant restaurant = new Restaurant();
        String name = "Chicken run";
        String introduction = "Most delicious chicken in this town.";
        String address = "Atlantis central park.";
        restaurant.setName(name);
        restaurant.setIntroduction(introduction);
        restaurant.setAddress(address);
        // then
        assertThat(restaurant.getName()).isEqualTo(name);
        assertThat(restaurant.getIntroduction()).isEqualTo(introduction);
        assertThat(restaurant.getAddress()).isEqualTo(address);
    }

}