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

    public void createOrder(String customerId, String basketId){
        Optional<Basket> basketOptional = basketRepository.readBasket(basketId);
        if (basketOptional.isEmpty()){
            throw new RuntimeException("Basket doesn't exist.");
        }
        Basket basket = basketOptional.get();
        String storeId = basket.getStoreId();
        Order order = new Order();
        Order order1 = addCustomerInfo(customerId, order);
        Order order2 = addStoreInfo(storeId, order1);
        order2.setMenuInBasketList(basket.getMenuInBasketList());
        orderRepository.createOrder(order2);
    }

    public Order addCustomerInfo(String customerId, Order order){
        order.setCustomerId(customerId);
        memberRepository.readMember(customerId).ifPresent(member -> {
            order.setCustomerId(member.getCustomerId());
            order.setCustomerName(member.getName());
            order.setCustomerPhoneNumber(member.getPhoneNumber());
            order.setCustomerAddress(member.getAddress());
            order.setCustomerAddressDetail(member.getAddressDetail());
            order.setCustomerLocation(member.getLocation());
        });
        return order;
    }

    public Order addStoreInfo(String storeId, Order order){
        storeRepository.readStore(storeId).ifPresent(store -> {
            order.setStoreId(store.getStoreId());
            order.setStoreName(store.getName());
            order.setStorePhoneNumber(store.getPhoneNumber());
            order.setStoreAddress(store.getAddress());
            order.setStoreAddressDetail(store.getAddressDetail());
            order.setStoreLocation(store.getLocation());
        });
        return order;
    }

    public Optional<List<Order>> getOrderList(String customerId){
        return orderRepository.readOrderList(customerId);
    }

    public Optional<Order> getOrder(String orderId){
        return orderRepository.readOrder(orderId);
    }
}
