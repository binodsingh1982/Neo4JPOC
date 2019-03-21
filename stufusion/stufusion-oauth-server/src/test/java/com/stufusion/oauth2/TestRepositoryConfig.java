package com.stufusion.oauth2;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.stufusion.oauth2.config.AppConfig;

@Configuration
@Import(AppConfig.class)
@PropertySource("classpath:test.properties")
@EnableAutoConfiguration
public class TestRepositoryConfig {

}
