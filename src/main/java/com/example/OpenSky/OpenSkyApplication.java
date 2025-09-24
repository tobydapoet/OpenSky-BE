package com.example.OpenSky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.OpenSky.repositories")
@EnableRedisRepositories(basePackages = "com.example.OpenSky.redis")
public class OpenSkyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenSkyApplication.class, args);
	}

}
