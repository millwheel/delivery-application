package msa.customer.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuTest {

    @Test
    void createMenuTest(){
        Menu menu = new Menu();
        String name = "golden olive chicken";
        int price = 20000;
        String description = "original ordinary chicken";
        menu.setName(name);
        menu.setPrice(price);
        menu.setDescription(description);
        assertThat(menu.getName()).isEqualTo(name);
        assertThat(menu.getPrice()).isEqualTo(price);
        assertThat(menu.getDescription()).isEqualTo(description);
    }

}