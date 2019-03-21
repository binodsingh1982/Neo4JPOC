package com.stufusion.oauth2.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = {"com.stufusion.oauth2.entity"})
@EnableJpaRepositories(basePackages = {"com.stufusion.oauth2.repository"})
@EnableTransactionManagement
public class RepositoryConfig {

}
