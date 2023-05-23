package msa.customer.service;

import msa.customer.DAO.FoodKindType;
import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import msa.customer.DTO.RestaurantForm;
import msa.customer.repository.restaurant.RestaurantRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Optional<String> getName(String id){
        return restaurantRepository.findById(id).map(Restaurant::getName);
    }

    public Optional<String> getPhoneNumber(String id){
        return restaurantRepository.findById(id).map(Restaurant::getPhoneNumber);
    }

    public Optional<String> getAddress(String id){
        return restaurantRepository.findById(id).map(Restaurant::getAddress);
    }

    public Optional<String> getAddressDetail(String id){
        return restaurantRepository.findById(id).map(Restaurant::getAddressDetail);
    }

    public Optional<Point> getCoordinates(String id){
        return restaurantRepository.findById(id).map(Restaurant::getLocation);
    }

    public Optional<String> getIntroduction(String id){
        return restaurantRepository.findById(id).map(Restaurant::getIntroduction);
    }

    public Optional<List<Menu>> getMenuList(String id){
        return restaurantRepository.findById(id).map(Restaurant::getMenuList);
    }

    public RestaurantForm getRestaurantInfo(String restaurantId){
        RestaurantForm restaurantForm = new RestaurantForm();
        getName(restaurantId).ifPresent(restaurantForm::setName);
        getPhoneNumber(restaurantId).ifPresent(restaurantForm::setPhoneNumber);
        getAddress(restaurantId).ifPresent(restaurantForm::setAddress);
        getAddressDetail(restaurantId).ifPresent(restaurantForm::setAddressDetail);
        getCoordinates(restaurantId).ifPresent(restaurantForm::setLocation);
        getIntroduction(restaurantId).ifPresent(restaurantForm::setIntroduction);
        getMenuList(restaurantId).ifPresent(restaurantForm::setMenuList);
        return restaurantForm;
    }

    public void createRestaurantInfo(RestaurantForm data){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(data.getId());
        restaurant.setName(data.getName());
        restaurant.setFoodKind(data.getFoodKind());
        restaurant.setPhoneNumber(data.getPhoneNumber());
        restaurant.setAddress(data.getAddress());
        restaurant.setAddressDetail(data.getAddressDetail());
        restaurant.setLocation(data.getLocation());
        restaurant.setIntroduction(data.getIntroduction());
        restaurant.setMenuList(data.getMenuList());
        restaurantRepository.create(restaurant);
    }

    public void updateRestaurantInfoIndividual(String restaurantId, RestaurantForm data){
        String name = data.getName();
        FoodKindType foodKind = data.getFoodKind();
        String phoneNumber = data.getPhoneNumber();
        String address = data.getAddress();
        String addressDetail = data.getAddressDetail();
        Point location = data.getLocation();
        String introduction = data.getIntroduction();
        List<Menu> menuList = data.getMenuList();
        if(name != null) restaurantRepository.updateName(restaurantId, name);
        if(foodKind != null) restaurantRepository.updateFoodKind(restaurantId, foodKind);
        if(phoneNumber != null) restaurantRepository.updatePhoneNumber(restaurantId, phoneNumber);
        if(address != null) restaurantRepository.updateAddress(restaurantId, address);
        if(addressDetail != null) restaurantRepository.updateAddressDetail(restaurantId, addressDetail);
        if(location != null) restaurantRepository.updateLocation(restaurantId, location);
        if(introduction != null) restaurantRepository.updateIntroduction(restaurantId, introduction);
        if(menuList != null) restaurantRepository.updateMenuList(restaurantId, menuList);
    }

    public void updateRestaurantInfoBulk(String restaurantId, RestaurantForm data){
        restaurantRepository.update(restaurantId, data);
    }

    public void openRestaurant(String restaurantId){
        restaurantRepository.setOpen(restaurantId, true);
    }

    public void closeRestaurant(String restaurantId){
        restaurantRepository.setOpen(restaurantId, false);
    }

    public List<Restaurant> showRestaurantListsNearCustomer(Point location, FoodKindType foodKind){
        return restaurantRepository.findRestaurantNear(location, foodKind);
    }
}
