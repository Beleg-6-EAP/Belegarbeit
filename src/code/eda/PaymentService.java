package demo.eda.service;

import demo.eda.event.OrderCreatedEvent;
import demo.eda.event.PaymentProcessedEvent;
import demo.eda.model.Payment;
import demo.eda.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReactiveKafkaProducerTemplate<String, PaymentProcessedEvent> producer;

    @KafkaListener(topics = OrderCreatedEvent.TOPIC, groupId = "payment-service")
    public Flux<Void> processPayment(ReceiverOptions<String, OrderCreatedEvent> receiverOptions) {
        return KafkaReceiver.create(receiverOptions)
                .receive()
                .flatMap(record -> {
                    final OrderCreatedEvent event = record.value();
                    final Payment payment = new Payment(UUID.randomUUID().toString(), event.getOrderId(), true);
                    return paymentRepository.save(payment).flatMap(savedPayment -> {
                        final PaymentProcessedEvent paymentEvent =
                            new PaymentProcessedEvent(
                                    savedPayment.getOrderId(),
                                    savedPayment.getId(),
                                    savedPayment.isSuccess());
                        return producer.send(PaymentProcessedEvent.TOPIC, paymentEvent).then();
                    });
                });
    }
}