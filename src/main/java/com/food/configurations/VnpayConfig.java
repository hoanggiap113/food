package com.food.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vnpay")
@Getter
@Setter
public class VnpayConfig {
    private String tmnCode;
    private String returnUrl;
    private String payUrl;
    private String hashSecret;
}
