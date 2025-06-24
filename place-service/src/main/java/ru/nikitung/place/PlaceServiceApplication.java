package ru.nikitung.place;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "ru.nikitung.place.feign")
public class PlaceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaceServiceApplication.class, args);
	}

}
