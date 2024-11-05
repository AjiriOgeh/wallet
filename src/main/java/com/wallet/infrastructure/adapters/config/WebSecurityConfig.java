//package com.wallet.infrastructure.adapters.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//
//@Configuration
//public class WebSecurityConfig {
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> {
//            web.ignoring().requestMatchers(
//                    HttpMethod.POST,
//                    "/public/**",
//                    "/users/**"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.GET,
//                    "/public/**"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.DELETE,
//                    "/public/**",
//                    "/users/{id}"
//            );
//        };
//    }
//}
