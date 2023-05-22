package msa.customer.repository.menu;

import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MenuRepositoryTest {
    private MenuRepository menuRepository;

    @Autowired
    MenuRepositoryTest(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @AfterEach
    void deleteAllMenu(){
        menuRepository.deleteAll();
    }

    @DisplayName("메뉴 저장 후 조회 테스트")
    @Test
    void createMenuTest(){
        // given
        Menu menu = new Menu();
        menu.setName("불고기 피자");
        menu.setPrice(18900);
        // when
        String id = menuRepository.make(menu);
        Menu storedMenu = menuRepository.findById(id).get();
        // then
        assertThat(storedMenu.getName()).isEqualTo(menu.getName());
        assertThat(storedMenu.getPrice()).isEqualTo(menu.getPrice());
    }

    @DisplayName("메뉴 가격 및 설명 수정")
    @Test
    void changeMenuPriceAndDescriptionTest(){
        // given
        Menu menu = new Menu();
        Restaurant restaurant = new Restaurant();
        restaurant.setName("착한피자");
        menu.setName("페퍼로니 피자");
        menu.setPrice(15900);
        menu.setDescription("페퍼로니가 올라간 맛있는 피자");
        menu.setRestaurant(restaurant);
        String id = menuRepository.make(menu);
        // when
        menuRepository.setPrice(id, 17900);
        menuRepository.setDescription(id, "페퍼로니 토핑이 흘러 넘치는 피자");
        Menu savedMenu = menuRepository.findById(id).get();
        // then
        assertThat(savedMenu.getPrice()).isEqualTo(17900);
        assertThat(savedMenu.getDescription()).isEqualTo("페퍼로니 토핑이 흘러 넘치는 피자");
    }
    
}