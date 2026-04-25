package com.customer.BillingSoftware.config;

import com.customer.BillingSoftware.auth.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfiguration {
}
