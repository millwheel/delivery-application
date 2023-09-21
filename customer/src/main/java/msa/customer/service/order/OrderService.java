package msa.customer.service.order;

import msa.customer.dto.rider.RiderPartDto;
import msa.customer.entity.basket.Basket;
import msa.customer.entity.order.Order;
import msa.customer.entity.order.OrderStatus;
import msa.customer.exception.BasketNonexistentException;
import msa.customer.exception.OrderNonexistentException;
import msa.customer.repository.basket.BasketRepository;
import msa.customer.repository.member.MemberRepository;
import msa.customer.repository.order.OrderRepository;
import msa.customer.repository.store.StoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public Order createOrder(String customerId, String basketId){
        Basket basket = basketRepository.readBasket(basketId).orElseThrow(() -> new BasketNonexistentException(basketId));
        String storeId = basket.getStoreId();
        Order order = new Order();
        Order order1 = addCustomerInfo(customerId, order);
        Order order2 = addStoreInfo(storeId, order1);
        Order order3 = addBasketInfo(basket, order2);
        String thisTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        order3.setOrderTime(thisTime);
        order3.setOrderStatus(OrderStatus.ORDER_REQUEST);
        return orderRepository.createOrder(order2);
    }

    public Order addCustomerInfo(String customerId, Order order){
        memberRepository.findById(customerId).ifPresent(member -> {
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

    public Order addBasketInfo(Basket basket, Order order){
        order.setMenuInBasketList(basket.getMenuInBasketList());
        order.setTotalPrice(basket.getTotalPrice());
        return order;
    }

    public List<Order> getOrderList(String customerId){
        return orderRepository.readOrderList(customerId);
    }

    public Order getOrder(String customerId, String orderId){
        return orderRepository.readOrder(customerId, orderId);
    }

    public void changeOrderStatusFromOtherServer(String orderId, OrderStatus orderStatus){
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }

    public void assignRiderToOrder(String orderId, OrderStatus orderStatus, RiderPartDto riderPartDto){
        orderRepository.updateRiderInfo(orderId, orderStatus, riderPartDto);
    }
}
