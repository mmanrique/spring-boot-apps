package com.mmanrique;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Sink.class)
public class ReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationApplication.class);
    }

    @Bean
    public CommandLineRunner getCommandLineRunner(final ReservationRepository reservationRepository) {
        return strings -> Stream.of("R1", "R2", "R3", "R4").forEach(s -> reservationRepository.save(new Reservation(s)));
    }
}
