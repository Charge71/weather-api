package com.charge71.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Centralized configuration for Swagger documentation framework.
 * 
 * @author Diego Bardari
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * Return an inizialized Swagger Docket with informations about the API.
	 * 
	 * @return an inizialized Swagger Docket with informations about the API
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/v1/**")).build().apiInfo(metaData())
				.tags(new Tag("weather", "Weather forecast operations")).useDefaultResponseMessages(false);
	}

	/**
	 * Return API informations.
	 * 
	 * @return API informations
	 */
	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Weather REST API").description("\"Weather REST API for city forecast\"")
				.version("0.0.1").build();
	}
}
