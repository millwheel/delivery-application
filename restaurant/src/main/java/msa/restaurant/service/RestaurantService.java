package msa.restaurant.service;

import msa.restaurant.DAO.FoodKindType;
import msa.restaurant.DAO.Menu;
import msa.restaurant.DAO.Restaurant;
import msa.restaurant.DTO.RestaurantForm;
import msa.restaurant.repository.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressService addressService;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, AddressService addressService) {
        this.restaurantRepository = restaurantRepository;
        this.addressService = addressService;
    }

    public Optional<Restaurant> getRestaurant(String restaurantId){
        return restaurantRepository.findById(restaurantId);
    }

    public Optional<String> getName(String restaurantId){
        return restaurantRepository.findById(restaurantId).map(Restaurant::getName);
    }

    public Optional<String> getPhoneNumber(String restaurantId){
        return restaurantRepository.findById(restaurantId).map(Restaurant::getPhoneNumber);
    }

    public Optional<String> getAddress(String restaurantId){
        return restaurantRepository.findById(restaurantId).map(Restaurant::getAddress);
    }

    public Optional<String> getAddressDetail(String restaurantId){
        return restaurantRepository.findById(restaurantId).map(Restaurant::getAddressDetail);
    }

    public Optional<Point> getCoordinates(String restaurantId){
        return restaurantRepository.findById(restaurantId).map(Restaurant::getLocation);
    }

    public Optional<String> getIntroduction(String restaurantId){
        return restaurantRepository.findById(restaurantId).map(Restaurant::getIntroduction);
    }

    public Optional<List<Menu>> getMenuList(String restaurantId){
        return restaurantRepository.findById(restaurantId).map(Restaurant::getMenuList);
    }

    public RestaurantForm getRestaurantInfo(String restaurantId){
        RestaurantForm restaurantForm = new RestaurantForm();
        getName(restaurantId).ifPresent(restaurantForm::setName);
        getPhoneNumber(restaurantId).ifPresent(restaurantForm::setPhoneNumber);
        getAddress(restaurantId).ifPresent(restaurantForm::setAddress);
        getAddressDetail(restaurantId).ifPresent(restaurantForm::setAddressDetail);
        getCoordinates(restaurantId).ifPresent(restaurantForm::setLocation);
        getIntroduction(restaurantId).ifPresent(restaurantForm::setIntroduction);
        return restaurantForm;
    }


    public String createRestaurantInfo(RestaurantForm data){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(data.getName());
        restaurant.setFoodKind(data.getFoodKind());
        restaurant.setPhoneNumber(data.getPhoneNumber());
        restaurant.setAddress(data.getAddress());
        Point coordinate = addressService.getCoordinate(data.getAddress());
        restaurant.setLocation(coordinate);
        restaurant.setAddressDetail(data.getAddressDetail());
        restaurant.setIntroduction(data.getIntroduction());
        return restaurantRepository.create(restaurant);
    }

    public void updateRestaurantInfo(String restaurantId, RestaurantForm data){
        String name = data.getName();
        FoodKindType foodKind = data.getFoodKind();
        String phoneNumber = data.getPhoneNumber();
        String address = data.getAddress();
        String addressDetail = data.getAddressDetail();
        String introduction = data.getIntroduction();
        if(name != null) restaurantRepository.updateName(restaurantId, name);
        if(foodKind != null) restaurantRepository.updateFoodKind(restaurantId, foodKind);
        if(phoneNumber != null) restaurantRepository.updatePhoneNumber(restaurantId, phoneNumber);
        if(address != null) {
            restaurantRepository.updateAddress(restaurantId, address);
            Point coordinate = addressService.getCoordinate(address);
            restaurantRepository.updateLocation(restaurantId, coordinate);
        }
        if(addressDetail != null) restaurantRepository.updateAddressDetail(restaurantId, addressDetail);
        if(introduction != null) restaurantRepository.updateIntroduction(restaurantId, introduction);
    }


    public void openRestaurant(String restaurantId){
        restaurantRepository.updateOpenStatus(restaurantId, true);
    }

    public void closeRestaurant(String restaurantId){
        restaurantRepository.updateOpenStatus(restaurantId, false);
    }

}
