package msa.customer.repository.restaurant;

import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RestaurantRepositoryTest {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantRepositoryTest(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
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
    void getNearRestaurantTest(){
        // given
        Restaurant restaurant = new Restaurant();
        Point pizzaCoordinate = new Point(127.080, 37.251);
        restaurant.setName("착한피자");
        restaurant.setLocation(pizzaCoordinate);
        restaurantRepository.make(restaurant);

        // when
        Point orderCoordinate = new Point(127.074, 37.252);
        List<Restaurant> restaurantNear = restaurantRepository.findRestaurantNear(orderCoordinate);
        // then
        assertThat(restaurantNear.get(0).getName()).isEqualTo("착한피자");
    }

    @DisplayName("4km 이상 떨어진 곳에 위치한 음식점은 조회하지 않는다.")
    @Test
    void notGetRestaurantOver4kmTest(){
        // given
        Restaurant restaurant = new Restaurant();
        Point pizzaCoordinate = new Point(127.018, 37.261);
        restaurant.setName("피자헤븐");
        restaurant.setLocation(pizzaCoordinate);
        restaurantRepository.make(restaurant);
        // when
        Point orderCoordinate = new Point(127.074, 37.253);
        List<Restaurant> restaurantNear = restaurantRepository.findRestaurantNear(orderCoordinate);
        // then
        assertThat(restaurantNear).isEmpty();
    }

}