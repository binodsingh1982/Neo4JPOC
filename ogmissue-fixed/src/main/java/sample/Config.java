package sample;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.web.support.OpenSessionInViewInterceptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import sample.repository.ModuleRepository;

/**
 * Created by buchgeher on 05.09.2016.
 */


@Configuration
@EnableNeo4jRepositories(basePackageClasses = {ModuleRepository.class})
@EnableTransactionManagement
@ComponentScan
@EnableWebMvc
@EnableConfigurationProperties(Neo4jProperties.class)
public class Config extends WebMvcConfigurerAdapter {

	private final Neo4jProperties properties;

	public Config(Neo4jProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public org.neo4j.ogm.config.Configuration configuration() {
		return this.properties.createConfiguration();
	}

	@Bean
	public OpenSessionInViewInterceptor openSessionInViewInterceptor() {
		OpenSessionInViewInterceptor openSessionInViewInterceptor =
				new OpenSessionInViewInterceptor();
		openSessionInViewInterceptor.setSessionFactory(sessionFactory(configuration()));
		return openSessionInViewInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addWebRequestInterceptor(openSessionInViewInterceptor());
	}

	@Bean
	public SessionFactory sessionFactory(org.neo4j.ogm.config.Configuration configuration) {
		return new SessionFactory(configuration, "sample.data", "sample.data2");
	}

	@Bean
	public Neo4jTransactionManager transactionManager(SessionFactory sessionFactory) {
		return new Neo4jTransactionManager(sessionFactory);
	}
}
