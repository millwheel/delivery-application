package msa.customer.service;

import msa.customer.repository.menu.MenuRepository;

public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }
}
