package msa.customer.service;

import msa.customer.entity.basket.Basket;
import msa.customer.entity.order.Order;
import msa.customer.repository.basket.BasketRepository;
import msa.customer.repository.member.MemberRepository;
import msa.customer.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;

    public OrderService(OrderRepository repository, MemberRepository memberRepository, BasketRepository basketRepository) {
        this.orderRepository = repository;
        this.memberRepository = memberRepository;
        this.basketRepository = basketRepository;
    }

    public void createOrder(String customerId, String storeId, String basketId){
        Order order = new Order();
        order.setCustomerId(customerId);
        memberRepository.findById(customerId).ifPresent(member -> {
            order.setCustomerName(member.getName());
            order.setCustomerPhoneNumber(member.getPhoneNumber());
            order.setCustomerAddress(member.getAddress());
            order.setCustomerAddressDetail(member.getAddressDetail());
            order.setCustomerLocation(member.getLocation());
        });
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isEmpty()){
            throw new RuntimeException("basket is empty.");
        }
        Basket basket = basketOptional.get();
        order.setMenuIdList(basket.getMenuIdList());
        order.setMenuCountList(basket.getMenuCountList());
        order.setMenuPriceList(basket.getMenuPriceList());
        orderRepository.create(order);
    }
}
