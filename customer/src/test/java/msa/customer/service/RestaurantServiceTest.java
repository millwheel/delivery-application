package msa.customer.service;

import msa.customer.DTO.RestaurantForm;
import msa.customer.repository.restaurant.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestaurantServiceTest {

    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;

    private final String ID = "1315-0045-7784-0159";
    private final String NAME = "착한피자";
    private final String PHONE_NUMBER = "050713753388";
    private final String ADDRESS = "경기도 수원시 영통구 청명로43번길 8";
    private final String ADDRESS_DETAIL = "1층";
    private final Point LOCATION = new Point(127.080, 37.251);
    private final String INTRO = "저희 착한 피자는 타 브랜드 피자와 달리 라지 14인치 미디움 10인치 입니다!";

    @Autowired
    RestaurantServiceTest(RestaurantService restaurantService, RestaurantRepository restaurantRepository) {
        this.restaurantService = restaurantService;
        this.restaurantRepository = restaurantRepository;
    }

    @DisplayName("음식점 정보 저장 후 조회")
    @Test
    void saveRestaurantInfoTest(){
        // given
        RestaurantForm restaurantForm = new RestaurantForm();
        restaurantForm.setName(NAME);
        restaurantForm.setPhoneNumber(PHONE_NUMBER);
        restaurantForm.setAddress(ADDRESS);
        restaurantForm.setAddressDetail(ADDRESS_DETAIL);
        restaurantForm.setLocation(LOCATION);
        restaurantForm.setIntroduction(INTRO);
        // when
        restaurantService.updateRestaurantInfo(ID, restaurantForm);
        RestaurantForm restaurantInfo = restaurantService.getRestaurantInfo(ID);
        // then
        assertThat(restaurantInfo.getName()).isEqualTo(NAME);
        assertThat(restaurantInfo.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(restaurantInfo.getAddress()).isEqualTo(ADDRESS);
        assertThat(restaurantInfo.getAddressDetail()).isEqualTo(ADDRESS_DETAIL);
        assertThat(restaurantInfo.getLocation()).isEqualTo(LOCATION);
        assertThat(restaurantInfo.getIntroduction()).isEqualTo(INTRO);
    }

}