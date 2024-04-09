package hodlene.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping(value="hello")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@RequestMapping(value="/{firstName}/{lastName}",method = RequestMethod.GET)
	public String getUserName(@PathVariable("firstName") String firstName,
							  @PathVariable("lastName") String lastName) {
		return String.format("{\"message\":\"Hello %s %s\"}", firstName, lastName);
	}
}