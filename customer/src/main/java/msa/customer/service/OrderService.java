package msa.customer.service;

import msa.customer.entity.basket.Basket;
import msa.customer.entity.order.Order;
import msa.customer.entity.store.Store;
import msa.customer.repository.basket.BasketRepository;
import msa.customer.repository.member.MemberRepository;
import msa.customer.repository.order.OrderRepository;
import msa.customer.repository.store.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;
    private final StoreRepository storeRepository;

    public OrderService(OrderRepository repository, MemberRepository memberRepository, BasketRepository basketRepository, StoreRepository storeRepository) {
        this.orderRepository = repository;
        this.memberRepository = memberRepository;
        this.basketRepository = basketRepository;
        this.storeRepository = storeRepository;
    }

    public Order createOrder(String customerId, String storeId, String basketId){
        Order order = new Order();
        Order order1 = addCustomerInfo(customerId, order);
        Order order2 = addStoreInfo(storeId, order1);
        Order order3 = addBasketInfo(basketId, order2);
        orderRepository.create(order3);
        return order3;
    }

    public Order addCustomerInfo(String customerId, Order order){
        order.setCustomerId(customerId);
        memberRepository.findById(customerId).ifPresent(member -> {
            order.setCustomerName(member.getName());
            order.setCustomerPhoneNumber(member.getPhoneNumber());
            order.setCustomerAddress(member.getAddress());
            order.setCustomerAddressDetail(member.getAddressDetail());
            order.setCustomerLocation(member.getLocation());
        });
<<<<<<< HEAD
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isEmpty()){
            throw new RuntimeException("basket is empty.");
        }
        Basket basket = basketOptional.get();
        order.setMenuIdList(basket.getMenuIdList());
        order.setMenuCountList(basket.getMenuCountList());
        order.setMenuPriceList(basket.getMenuPriceList());
        orderRepository.create(order);
=======
        return order;
    }

    public Order addStoreInfo(String storeId, Order order){
        Optional<Store> storeOptional = storeRepository.findById(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Store doesn't exist");
        }
        Store store = storeOptional.get();
        order.setStoreId(store.getStoreId());
        order.setStoreName(store.getName());
        order.setStorePhoneNumber(store.getPhoneNumber());
        order.setStoreAddress(store.getAddress());
        order.setStoreAddressDetail(store.getAddressDetail());
        order.setStoreLocation(store.getLocation());
        return order;
    }

    public Order addBasketInfo(String basketId, Order order){
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isEmpty()){
            throw new RuntimeException("Basket doesn't exist.");
        }
        Basket basket = basketOptional.get();
        order.setMenuInBasketList(basket.getMenuInBasketList());
        return order;
    }

    public Optional<Order> getOrder(String orderId){
        return orderRepository.findById(orderId);
>>>>>>> 8a3f8f325f90bd671483f95c516aed65b56d13d2
    }
}
