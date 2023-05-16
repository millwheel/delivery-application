package msa.customer.repository.restaurant;

import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import msa.customer.repository.menu.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RestaurantRepositoryTest {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantRepositoryTest(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @DisplayName("")
    @Test
    void createRestaurantTest(){
        // given
        Menu menu = new Menu();
        menu.setName("콤비네이션피자");
        menu.setPrice(16900);
        menu.setDescription("갖가지 토핑이 잘 어우러진 균형잡힌 피자");

        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);

        Restaurant restaurant = new Restaurant();
        restaurant.setName("착한피자");
        restaurant.setMenuList(menuList);
        // when
        String id = restaurantRepository.make(restaurant);
        Restaurant savedRestaurant = restaurantRepository.findById(id).get();
        savedRestaurant.getMenuList().stream().forEach(v -> {
            System.out.println(v.getName());
            System.out.println(v);
        });
        System.out.println(menu.getName());
        System.out.println(menu);
        // then
        assertThat(savedRestaurant.getName()).isEqualTo("착한피자");
        assertThat(savedRestaurant.getMenuList()).contains(menu);
    }

}