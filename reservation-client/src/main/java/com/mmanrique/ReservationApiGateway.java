package com.mmanrique;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
@EnableBinding(Source.class)
public class ReservationApiGateway {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Source reservationMessageService;


    @RequestMapping(method = RequestMethod.GET, value = "/names")
    @HystrixCommand(fallbackMethod = "defaultReservationNames")
    public Collection<String> getReservationNames() {

        ParameterizedTypeReference<Resources<Reservation>> parameterizedTypeReference = new ParameterizedTypeReference<Resources<Reservation>>() {
        };

        ResponseEntity<Resources<Reservation>> entity = restTemplate.exchange("http://reservation-service/reservations", HttpMethod.GET, null, parameterizedTypeReference);
        return entity.getBody().getContent().stream().map(Reservation::getReservationName).collect(Collectors.toList());
    }

    private Collection<String> defaultReservationNames() {
        return Collections.emptyList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/post")
    public void saveReservation() {
        Message<String> msg = MessageBuilder.withPayload("R5" + new Random().nextInt(100)).build();
        this.reservationMessageService.output().send(msg);
    }
}
