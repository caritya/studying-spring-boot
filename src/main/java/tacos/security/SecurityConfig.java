package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.User;
import tacos.data.UserRepository;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			User user= userRepo.findByUsername(username);
			if(user !=null) return user;
	//TODO Сделать чтоб в веб-интефейс выдавалось сообщение, что пользователь не обнаружен		
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};

	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeRequests()
				 .antMatchers("/design","/orders").access("hasRole('USER')")
				 .antMatchers("/","/**").access("permitAll()")
				
				.and()
				 .formLogin()
				  .loginPage("/login")
				   .defaultSuccessUrl("/design", true)
				
				.and()
		         .logout()
		          .logoutSuccessUrl("/")
				 
		          // Make H2-Console non-secured; for debug purposes
				.and()
				  .csrf()
				   	.ignoringAntMatchers("/h2-console/**")
				
				   	// Allow pages to be loaded in frames from the same origin; needed for H2-Console
			      .and()
			        .headers()
			          .frameOptions()
			            .sameOrigin()
			//;
				.and()
				.build();
	}
}
