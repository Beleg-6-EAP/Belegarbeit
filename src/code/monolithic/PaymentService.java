package demo.monolith.service;

import demo.monolith.model.Payment;
import demo.monolith.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void processPayment(String orderId) {
        final Payment payment = new Payment(UUID.randomUUID().toString(), orderId, true);
        paymentRepository.save(payment);
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }
}