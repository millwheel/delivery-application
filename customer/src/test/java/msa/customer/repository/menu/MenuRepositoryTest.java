package msa.customer.repository.menu;

import msa.customer.DAO.Member;
import msa.customer.DAO.Menu;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MenuRepositoryTest {
    private MenuRepository menuRepository;

    @Autowired
    MenuRepositoryTest(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @DisplayName("메뉴 저장 후 조회 테스트")
    @Test
    void saveMenu(){
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
    
    
}