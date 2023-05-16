package msa.customer.service;

import msa.customer.DAO.Coordinates;
import msa.customer.DAO.Member;
import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import msa.customer.DTO.RestaurantForm;
import msa.customer.repository.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<Coordinates> getCoordinates(String id){
        return restaurantRepository.findById(id).map(Restaurant::getCoordinates);
    }

    public Optional<String> getIntroduction(String id){
        return restaurantRepository.findById(id).map(Restaurant::getIntroduction);
    }

    public Optional<List<Menu>> getMenuList(String id){
        return restaurantRepository.findById(id).map(Restaurant::getMenuList);
    }

    public RestaurantForm getRestaurantInfo(String id){
        RestaurantForm restaurantForm = new RestaurantForm();
        getName(id).ifPresent(restaurantForm::setName);
        getPhoneNumber(id).ifPresent(restaurantForm::setPhoneNumber);
        getAddress(id).ifPresent(restaurantForm::setAddress);
        getAddressDetail(id).ifPresent(restaurantForm::setAddressDetail);
        getCoordinates(id).ifPresent(restaurantForm::setCoordinates);
        getIntroduction(id).ifPresent(restaurantForm::setIntroduction);
        getMenuList(id).ifPresent(restaurantForm::setMenuList);
        return restaurantForm;
    }

    public void updateRestaurantInfo(String id, RestaurantForm data){
        String name = data.getName();
        String phoneNumber = data.getPhoneNumber();
        String address = data.getAddress();
        String addressDetail = data.getAddressDetail();
        Coordinates coordinates = data.getCoordinates();
        String introduction = data.getIntroduction();
        List<Menu> menuList = data.getMenuList();
        if(name != null) restaurantRepository.setName(id, name);
        if(phoneNumber != null) restaurantRepository.setPhoneNumber(id, phoneNumber);
        if(address != null) restaurantRepository.setAddress(id, address);
        if(addressDetail != null) restaurantRepository.setAddressDetail(id, addressDetail);
        if(coordinates != null) restaurantRepository.setCoordinates(id, coordinates);
        if(introduction != null) restaurantRepository.setIntroduction(id, introduction);
        if(menuList != null) restaurantRepository.setMenuList(id, menuList);
    }

}
