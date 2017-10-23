package com.utfpr.tattool.api.apitattool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.context.config.annotation.EnableContextCredentials;

@SpringBootApplication
@EnableAutoConfiguration
@EnableContextCredentials(accessKey="AKIAJKMB7ZN4NOORD6EQ", secretKey="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")
public class ApiTattoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTattoolApplication.class, args);
	}
}
