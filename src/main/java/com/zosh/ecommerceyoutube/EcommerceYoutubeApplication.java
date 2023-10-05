package com.zosh.ecommerceyoutube;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceYoutubeApplication {
	private static final Logger loger = LogManager.getLogger(EcommerceYoutubeApplication.class.getName());

	public static void main(String[] args) {
		loger.debug("Enter into the EcommerceYoutubeApplication");
		SpringApplication.run(EcommerceYoutubeApplication.class, args);
		loger.debug("Exit into the EcommerceYoutubeApplication");
	}

}
