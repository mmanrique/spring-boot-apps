package com.mmanrique;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class ReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationApplication.class);
    }

    @Bean
    public CommandLineRunner getCommandLineRunner(final ReservationRepository reservationRepository) {
        return strings -> Stream.of("R1", "R22", "R3", "R4").forEach(s -> reservationRepository.save(new Reservation(s)));
    }
}
