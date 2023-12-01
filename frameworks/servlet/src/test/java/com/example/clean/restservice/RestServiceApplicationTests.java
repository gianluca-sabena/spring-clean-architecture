
// From https://github.com/spring-guides/gs-rest-service/blob/main/complete/src/test/java/com/example/restservice/GreetingControllerTests.java
package com.example.clean.restservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class RestServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void noParamGreetingShouldReturnDefaultMessage() throws Exception {

		this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("[{\"id\":\"00000000-0000-0000-0000-000000000000\",\"email\":\"email@domain.com\",\"lastName\":\"Last Name\",\"firstName\":\"First Name\"}]")));
	}


}
