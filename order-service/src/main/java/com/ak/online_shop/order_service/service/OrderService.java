package com.ak.online_shop.order_service.service;

import com.ak.online_shop.order_service.client.InventoryClient;
import com.ak.online_shop.order_service.dto.OrderRequest;
import com.ak.online_shop.order_service.model.Order;
import com.ak.online_shop.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public String createOrder(OrderRequest orderRequest) {
        log.info(orderRequest.toString());

        boolean isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (!isInStock) throw new RuntimeException("Product " + orderRequest.skuCode() + " isn't in stock");

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
