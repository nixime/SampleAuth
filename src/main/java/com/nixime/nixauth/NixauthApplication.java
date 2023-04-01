package com.nixime.nixauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.nixime.nixauth.config.RsaKeyProperties;
import com.nixime.nixauth.config.AuthenticationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeyProperties.class,AuthenticationProperties.class})
public class NixauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(NixauthApplication.class, args);
	}

}
