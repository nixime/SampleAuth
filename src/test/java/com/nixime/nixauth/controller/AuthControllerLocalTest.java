package com.nixime.nixauth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nixime.nixauth.config.SecurityConfig;
import com.nixime.nixauth.service.TokenService;

@WebMvcTest({ AuthController.class })
@Import({ SecurityConfig.class, TokenService.class })
@TestPropertySource(properties = "auth.type=local")
class AuthControllerLocalTest {

	@Autowired
	MockMvc mvc;

	@Test
	void tokenWhenAnonymousThenStatusIsUnauthorized() throws Exception {
		this.mvc.perform(post("/token")).andExpect(status().isUnauthorized());
	}

	@Test
	void tokenWithBasicThenGetToken() throws Exception {
		MvcResult result = this.mvc.perform(post("/token")
			.with(httpBasic("sakura", "password")))
			.andExpect(status().isOk())
			.andReturn();

		assertThat(result.getResponse().getContentAsString()).isNotEmpty();
	}
}