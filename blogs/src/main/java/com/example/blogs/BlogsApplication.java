package com.example.blogs;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogsApplication {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Spring Boot REST API")
						.version("3.0.0")
						.description("Spring Boot application API documentation")
						.contact(new Contact()
								.name("Your Name")
								.email("your.email@example.com")
								.url("https://your-website.com"))
						.license(new License()
								.name("Apache 2.0")
								.url("https://springdoc.org")))
				.externalDocs(new ExternalDocumentation()
						.description("Project GitHub Repository")
						.url("https://github.com/your-repo"));
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("public")
				.pathsToMatch("/api/**")
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogsApplication.class, args);
	}

}
