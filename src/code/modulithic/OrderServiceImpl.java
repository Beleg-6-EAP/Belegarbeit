package demo.modulith.order.internal;

import demo.modulith.order.OrderService;
import demo.modulith.payment.PaymentService;
import demo.modulith.payment.internal.PaymentServiceImpl;
import demo.modulith.shipment.ShipmentService;
import demo.modulith.shipment.internal.ShipmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ShipmentService shipmentService;

    public Order create(Order order) {
        final Order savedOrder = orderRepository.save(order);
        paymentService.processPayment(savedOrder.getId());
        shipmentService.handleShipping(savedOrder.getId());

        return savedOrder;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}