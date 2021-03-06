package pro.abacus.webproject.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;
import pro.abacus.webproject.domain.User;
import pro.abacus.webproject.services.UserService;
import pro.abacus.webproject.services.ValidationService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@WithMockUser(roles = "USER")
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private ValidationService validationService;

	@MockBean
	private Errors errors;

	@MockBean
	private User user;

	@Autowired
	private HomeController homeController = new HomeController(userService, validationService);

	@Test
	public void shouldShowHomePage() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("home"));
	}

	@Test
	public void shouldShowRegistrationForm() throws Exception {
		mockMvc.perform(get("/registration"))
				.andExpect(status().isOk())
				.andExpect(view().name("registration"))
				.andExpect(model().attributeExists("user"));
	}

	@Test
	public void shouldShowLoginForm() throws Exception {
		mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"));
	}

	@Test
	public void shouldPostRegistrationDetails() throws Exception {


		mockMvc.perform(post("/registration")
				.param("userID", "123")
				.param("name", "test1")
				.param("email", "test1@gmail.com")
				.param("password", "password1")
		)
				.andExpect(status().isOk());
	}

	@Test
	public void shouldSaveUser() throws Exception {
		when(validationService.validate(user, errors)).thenReturn(true);

		homeController.processRegistrationForm(user, errors);

		verify(validationService).validate(user, errors);
		verify(userService).save(user);
	}

	@Test
	public void shouldPostLoginDetails() throws Exception {
		mockMvc.perform(post("/registration")
				.param("userID", "123")
				.param("name", "test1")
				.param("email", "test1@gmail.com")
				.param("password", "password1")
		)
				.andExpect(status().isOk());


		mockMvc.perform(post("/login")
				.param("name", "test9")
				.param("password", "test9")
		)
				.andExpect(status().isOk());
	}
}
