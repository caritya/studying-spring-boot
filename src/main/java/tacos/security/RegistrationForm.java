package tacos.security;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;
import tacos.User;

@Data
public class RegistrationForm {

	@NotBlank(message = "username is required")
	private String username;
	@NotBlank(message = "password is required")
	private String password;
	@NotBlank(message = "fullname is required")
	private String fullname;
	@NotBlank(message = "street is required")
	private String street;
	@NotBlank(message = "city is required")
	private String city;
	@NotBlank(message = "state is required")
	private String state;
	@NotBlank(message = "zip code is required")
	private String zip;
	@NotBlank(message = "telephone number is required")
	private String phone;

	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(
				username, passwordEncoder.encode(password), 
				fullname, street, city, state, zip, phone);
	}
}
