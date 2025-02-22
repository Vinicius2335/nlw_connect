package com.github.vinicius2335.connect.api;

import com.github.vinicius2335.connect.api.core.utils.GenerateSwaggerJson;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Log4j2
public class NlwConnectApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NlwConnectApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner swagger(GenerateSwaggerJson generateSwaggerJson){
		return args -> {
			log.info("Swagger UI => http://localhost:8080/swagger/docs");

			generateSwaggerJson.execute();
		};
	}
}
