package com.mmanrique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

@MessageEndpoint
public class RabbitMessageEndpoint {

    @Autowired
    private ReservationRepository reservationRepository;

    @ServiceActivator(inputChannel = "input")
    public void acceptNewMessage(Message<String> message) {
        String reservationName = message.getPayload();
        this.reservationRepository.save(new Reservation(reservationName));
    }
}
