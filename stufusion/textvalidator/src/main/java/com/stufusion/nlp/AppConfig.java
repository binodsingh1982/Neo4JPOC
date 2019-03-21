package com.stufusion.nlp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Sunil
 *
 */
@Configuration
@PropertySource({ "classpath:application.properties" })
@ComponentScan("com.stufusion")
@EnableAsync
public class AppConfig {

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		return builder;
	}
}