package com.example.biggestpicture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class BiggestpictureApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiggestpictureApplication.class, args);
	}

	@Scheduled(cron = "0 1 * * * ?")
	@CacheEvict(value = "LargestPictures", allEntries = true)
	public void clearCache() {
		System.out.println("Clearing largest pictures cache!");
	}

}
