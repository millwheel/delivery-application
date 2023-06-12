package msa.customer.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StoreTest {

    @Test
    void createRestaurantTest(){
        // when
        Store store = new Store();
        String name = "Chicken run";
        String introduction = "Most delicious chicken in this town.";
        String address = "Atlantis central park.";
        store.setName(name);
        store.setIntroduction(introduction);
        store.setAddress(address);
        // then
        assertThat(store.getName()).isEqualTo(name);
        assertThat(store.getIntroduction()).isEqualTo(introduction);
        assertThat(store.getAddress()).isEqualTo(address);
    }

}