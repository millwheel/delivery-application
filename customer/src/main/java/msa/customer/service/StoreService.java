package msa.customer.service;

import msa.customer.dto.menu.MenuSqsDto;
import msa.customer.entity.FoodKindType;
import msa.customer.entity.MenuPartInfo;
import msa.customer.entity.Store;
import msa.customer.dto.store.StoreSqsDto;
import msa.customer.repository.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }


    public void createStore(StoreSqsDto data){
        Store store = new Store();
        store.setStoreId(data.getStoreId());
        store.setName(data.getName());
        store.setFoodKind(data.getFoodKind());
        store.setPhoneNumber(data.getPhoneNumber());
        store.setAddress(data.getAddress());
        store.setAddressDetail(data.getAddressDetail());
        store.setLocation(data.getLocation());
        store.setIntroduction(data.getIntroduction());
        storeRepository.create(store);
    }

    public Optional<Store> getStore(String storeId){
        return storeRepository.findById(storeId);
    }

    public void updateStore(StoreSqsDto data){
        storeRepository.update(data);
    }

    public void deleteStore(String storeId){
        storeRepository.deleteById(storeId);
    }

    public void openStore(String storeId){
        storeRepository.updateOpenStatus(storeId, true);
    }

    public void closeStore(String storeId){
        storeRepository.updateOpenStatus(storeId, false);
    }

    public List<Store> showStoreListsNearCustomer(Point location, FoodKindType foodKind){
        return storeRepository.findStoreNear(location, foodKind);
    }

    public List<MenuPartInfo> getMenuList(String storeId){
        return storeRepository.findById(storeId).map(Store::getMenuList).orElseGet(ArrayList::new);
    }

    public void addToMenuList(MenuSqsDto menuSqsDto){
        String storeId = menuSqsDto.getStoreId();
        List<MenuPartInfo> menuList = getMenuList(storeId);
        MenuPartInfo menuPartInfo = new MenuPartInfo();
        menuPartInfo.setMenuId(menuSqsDto.getMenuId());
        menuPartInfo.setName(menuSqsDto.getName());
        menuPartInfo.setPrice(menuSqsDto.getPrice());
        menuList.add(menuPartInfo);
        storeRepository.updateMenuList(storeId, menuList);
    }

    public void updateMenuFromList(MenuSqsDto menuSqsDto){
        String storeId = menuSqsDto.getStoreId();
        List<MenuPartInfo> menuList = getMenuList(storeId);
        MenuPartInfo menuPartInfo = new MenuPartInfo();
        menuPartInfo.setMenuId(menuSqsDto.getMenuId());
        menuPartInfo.setName(menuSqsDto.getName());
        menuPartInfo.setPrice(menuSqsDto.getPrice());
        int index = menuList.indexOf(menuPartInfo);
        menuList.set(index, menuPartInfo);
        storeRepository.updateMenuList(storeId, menuList);
    }
    public void deleteMenuFromList(String storeId, String menuId){
        List<MenuPartInfo> menuList = getMenuList(storeId);
        menuList.removeIf(menuPartInfo -> menuPartInfo.getMenuId().equals(menuId));
        storeRepository.updateMenuList(storeId, menuList);
    }
}
