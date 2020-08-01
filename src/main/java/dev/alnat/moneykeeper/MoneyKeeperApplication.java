package dev.alnat.moneykeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MoneyKeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyKeeperApplication.class, args);
	}

}
