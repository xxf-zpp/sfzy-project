package com.xu.user.config;

import me.ihxq.projects.pna.PhoneNumberLookup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhoneGeoConfig {
    @Bean
    public PhoneNumberLookup phoneNumberLookup() {
        return new PhoneNumberLookup();
    }
}