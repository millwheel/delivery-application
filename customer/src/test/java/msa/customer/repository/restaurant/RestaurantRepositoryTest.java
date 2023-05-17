package msa.customer.repository.restaurant;

import msa.customer.DAO.Location;
import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import msa.customer.repository.menu.MenuRepository;
import org.junit.jupiter.api.AfterEach;
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
    private final MenuRepository menuRepository;

    @Autowired
    RestaurantRepositoryTest(MenuRepository menuRepository, RestaurantRepository restaurantRepository, MenuRepository menuRepository1) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository1;
    }

    @AfterEach
    void deleteAllTestData(){
        restaurantRepository.deleteAll();
    }

    @DisplayName("음식점 정보 저장 후 조회한다.")
    @Test
    void saveRestaurantTest(){
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
        // then
        assertThat(savedRestaurant.getName()).isEqualTo("착한피자");
        assertThat(savedRestaurant.getMenuList().get(0).getName()).contains(menu.getName());
    }

    @DisplayName("주어진 좌표 근방 음식점 조회한다.")
    @Test
    void test(){
        // given
        Restaurant restaurant = new Restaurant();
        Location pizzaCoordinate = new Location(37.251414, 127.080051);
        restaurant.setName("착한피자");
        restaurant.setLocation(pizzaCoordinate);
        restaurantRepository.make(restaurant);
        // when
        Location orderCoordinate = new Location(37.252962, 127.074563);
        List<Restaurant> restaurantNear = restaurantRepository.findRestaurantNear(orderCoordinate);
        // then
        assertThat(restaurantNear.get(0).getName()).isEqualTo(restaurant.getName());
    }

}