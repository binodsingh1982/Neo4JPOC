package com.stufusion.oauth2.config;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${auth.client.resourceid:stufusion-apps}")
    private String resourceIds;

    @Value("${auth.server.accesstoken.validity.period:900}")
    private int accessTokenValiditySeconds;

    @Value("${auth.server.jwt.symmetric.key:123}")
    private String jwtKey;

    @Value("${auth.server.refreshtoken.validity.period:1800}")
    private int refreshTokenValiditySeconds;

    @Value("${auth.client.secret:1800}")
    private String secret;

    @Value("${auth.client.clientid:stufusion-trusted-client}")
    private String clientId;

    @Value("${auth.client.client.redirecturl:http://localhost/callback}")
    private String redirectUrl;

    @Value("${auth.server.keystore.pass:secret}")
    private String keystorePass;

    @Value("${auth.server.keypair.pass:secret}")
    private String keyPairPass;

    @Value("${auth.server.keypair.name:stufusion}")
    private String keyPairName;

    @Value("${classpath:cert/stufusion.jks}")
    private ClassPathResource serverJKS;

    private String[] resourceIdArr;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostConstruct
    public void afterPropertiesSet() {
        resourceIdArr = resourceIds.split(",");
        Assert.notEmpty(resourceIdArr);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter()));// we might need this if we get a
                                                                                    // custom token enhancer and it
                                                                                    // should be first in list

        endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory().withClient(clientId)
                .authorizedGrantTypes("authorization_code", "password", "implicit", "refresh_token")
                .authorities("ROLE_STUFUSION_TRUSTED_CLIENT").scopes("read", "write", "delete")
                .resourceIds(resourceIdArr).secret(secret).redirectUris(redirectUrl)
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds);

    }

    @Bean
    public ApprovalStoreUserApprovalHandler userApprovalHandler() {
        ApprovalStoreUserApprovalHandler handler = new ApprovalStoreUserApprovalHandler();
        handler.setApprovalStore(approvalStore());
        handler.setClientDetailsService(clientDetailsService);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        return handler;
    }

    @Bean
    public ApprovalStore approvalStore() {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore());
        return store;
    }

    @Bean
    @Primary
    public AuthorizationServerTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public RestTemplate resourceServerOAuthRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(serverJKS, keystorePass.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyPairName, keyPairPass.toCharArray()));
        return converter;
    }

    // @Bean
    public JwtAccessTokenConverter accessTokenConverter2() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtKey);
        return converter;
    }

}
