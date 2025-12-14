package com.ak.online_shop.order_service.service;

import com.ak.online_shop.order_service.dto.OrderRequest;
import com.ak.online_shop.order_service.model.Order;
import com.ak.online_shop.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public String createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .skuCode(orderRequest.skuCode())
                .price(orderRequest.price())
                .quantity(orderRequest.quantity())
                .build();

        order.setOrderNumber(UUID.randomUUID().toString());

        orderRepository.save(order);

        return "Order created successfully";
    }
}
