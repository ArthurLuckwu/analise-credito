package com.cdt.credito;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cdt.credito.auth.controller.AuthController;

public class AuthControllerTest extends CreditoApplicationTests {
	
	private MockMvc mockMvc;

	@Autowired
	private AuthController authController;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}
	
	@Test
	public void testGETCapthaAuthController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/auth/captcha")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
}
