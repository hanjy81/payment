package com.example.payment;

import com.example.payment.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler {
    @Autowired PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalPlaced_Payment(@Payload RentalPlaced rentalPlaced){

        if(!rentalPlaced.validate()) return;

        System.out.println("\n\n##### listener payment : " + rentalPlaced.toJson() + "\n\n");

        // Sample Logic //
        Payment payment = new Payment();
        payment.setRentalId(rentalPlaced.getId());
        payment.setProductId(rentalPlaced.getProductId());
        payment.setStatus("payment");
        payment.setAmt(rentalPlaced.getAmt());
        paymentRepository.save(payment);            
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}
}
