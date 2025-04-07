package com.food.configurations;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;
    private Duration ttl;

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setTtl(Duration ttl) {
        this.ttl = ttl;
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        RSAKey jwk = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();

        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    public Duration getTtl() {
        return ttl;
    }
}
