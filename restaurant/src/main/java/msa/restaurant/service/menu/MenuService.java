package msa.restaurant.service.menu;

import lombok.AllArgsConstructor;
import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.dto.menu.MenuSqsDto;
import msa.restaurant.entity.menu.Menu;
import msa.restaurant.message_queue.SendingMessageConverter;
import msa.restaurant.message_queue.SqsService;
import msa.restaurant.repository.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;

    public String createMenu(MenuRequestDto data, String storeId){
        Menu menu = Menu.builder().name(data.getName()).price(data.getPrice()).description(data.getDescription()).storeId(storeId).build();
        Menu savedMenu = menuRepository.create(menu);
        MenuSqsDto menuSqsDto = new MenuSqsDto(savedMenu);
        String messageForMenuInfo = sendingMessageConverter.createMessageToCreateMenu(menuSqsDto);
        sqsService.sendToCustomer(messageForMenuInfo);
        sqsService.sendToRider(messageForMenuInfo);
        return savedMenu.getMenuId();
    }

    public Menu getMenu(String menuId){
        return menuRepository.readMenu(menuId);
    }

    public List<Menu> getMenuList(String storeId){
        return menuRepository.readMenuList(storeId);
    }

    public Menu updateMenu(String menuId, MenuRequestDto data){
        Menu menu = menuRepository.update(menuId, data);
        MenuSqsDto menuSqsDto = new MenuSqsDto(menu);
        String messageToUpdateMenu = sendingMessageConverter.createMessageToUpdateMenu(menuSqsDto);
        sqsService.sendToCustomer(messageToUpdateMenu);
        sqsService.sendToRider(messageToUpdateMenu);
        return menu;
    }

    public void deleteMenu(String storeId, String menuId){
        menuRepository.delete(menuId);
        String messageToDeleteMenu = sendingMessageConverter.createMessageToDeleteMenu(storeId, menuId);
        sqsService.sendToCustomer(messageToDeleteMenu);
        sqsService.sendToRider(messageToDeleteMenu);
    }
}
