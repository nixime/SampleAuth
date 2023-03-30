package com.nixime.nixauth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nixime.nixauth.config.SecurityConfig;
import com.nixime.nixauth.service.TokenService;

@WebMvcTest({ HomeController.class, AuthController.class })
@Import({ SecurityConfig.class, TokenService.class })
class HomeControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	void rootWhenUnauthenticatedThen401() throws Exception {
		this.mvc.perform(get("/")).andExpect(status().isUnauthorized());
	}

	@Test
    void rootWhenAuthenticatedThenSaysHelloUser() throws Exception {
        MvcResult result = this.mvc.perform(post("/token")
				.with(httpBasic("sakura", "password")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        result = this.mvc.perform(get("/")
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
                .andReturn();
		
		String value = result.getResponse().getContentAsString();
		
		assertEquals("Hello, sakura", value);
    }

	@Test
	public void rootWithBasicStatusIsUnauthorized() throws Exception {
		this.mvc.perform(get("/").with(httpBasic("sakura", "password"))).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser
	public void rootWithMockUserStatusIsOK() throws Exception {
		this.mvc.perform(get("/")).andExpect(status().isOk());
	}

}