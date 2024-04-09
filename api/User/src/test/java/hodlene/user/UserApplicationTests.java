package hodlene.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserApplication.class)
class UserApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHello() throws Exception {
		// API 호출
		mockMvc.perform(get("/hello/firstNameTest/lastNameTest"))
				// HTTP 상태 코드 검증
				.andExpect(status().isOk())
				// 응답 본문 검증
				.andExpect(content().string("{\"message\":\"Hello firstNameTest lastNameTest\"}"));
	}
}
